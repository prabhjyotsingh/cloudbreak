package com.sequenceiq.redbeams.service.dbconfig;

import static com.sequenceiq.cloudbreak.common.service.TransactionService.TransactionExecutionException;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Optional;
import java.util.function.Supplier;

import java.sql.SQLException;

import org.hibernate.exception.ConstraintViolationException;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.stubbing.Answer;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.access.AccessDeniedException;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.ResourceStatus;
import com.sequenceiq.cloudbreak.common.service.Clock;
import com.sequenceiq.cloudbreak.common.service.TransactionService;
import com.sequenceiq.cloudbreak.exception.NotFoundException;
import com.sequenceiq.cloudbreak.exception.BadRequestException;
import com.sequenceiq.redbeams.domain.DatabaseConfig;
import com.sequenceiq.redbeams.repository.DatabaseConfigRepository;
import com.sequenceiq.redbeams.service.crn.CrnService;
import com.sequenceiq.redbeams.service.crn.CrnServiceTest;

public class DatabaseConfigServiceTest {

    private static final long CURRENT_TIME_MILLIS = 1000L;

    private static final String CRN = "crn";

    private static final String DATABASE_NAME = "databaseName";

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Mock
    private DatabaseConfigRepository databaseConfigRepository;

    @Mock
    private Clock clock;

    @Mock
    private CrnService crnService;

    @Mock
    private TransactionService transactionService;

    @InjectMocks
    private DatabaseConfigService underTest;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
    }

    @Test
    public void testRegister() {
        DatabaseConfig configToRegister = new DatabaseConfig();
        when(clock.getCurrentTimeMillis()).thenReturn(CURRENT_TIME_MILLIS);
        when(crnService.createDatabaseCrn()).thenReturn(CrnServiceTest.getValidCrn());

        underTest.register(configToRegister);

        verify(databaseConfigRepository).save(configToRegister);
        assertEquals(CURRENT_TIME_MILLIS, configToRegister.getCreationDate().longValue());
        assertEquals(ResourceStatus.USER_MANAGED, configToRegister.getStatus());
        assertNotNull(configToRegister.getCrn());
    }

    @Test
    public void testDeleteRegisteredDatabase() throws TransactionExecutionException {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setStatus(ResourceStatus.USER_MANAGED);
        when(databaseConfigRepository.findByName(DATABASE_NAME)).thenReturn(Optional.of(databaseConfig));
        setupTransactionServiceRequired();

        underTest.delete(DATABASE_NAME);

        assertTrue(databaseConfig.isArchived());
        verify(databaseConfigRepository).save(databaseConfig);
    }

    @Test
    public void testDeleteNotFound() throws TransactionExecutionException {
        thrown.expect(NotFoundException.class);
        when(databaseConfigRepository.findByName(DATABASE_NAME)).thenReturn(Optional.empty());
        setupTransactionServiceRequired();

        underTest.delete(DATABASE_NAME);
    }

    @Test
    public void testDeleteCreatedDatabase() throws TransactionExecutionException {
        DatabaseConfig databaseConfig = new DatabaseConfig();
        databaseConfig.setStatus(ResourceStatus.DEFAULT);
        when(databaseConfigRepository.findByName(DATABASE_NAME)).thenReturn(Optional.of(databaseConfig));
        setupTransactionServiceRequired();

        underTest.delete(DATABASE_NAME);

        assertTrue(databaseConfig.isArchived());
        verify(databaseConfigRepository).save(databaseConfig);
    }

    @Test
    public void testRegisterEntityWithNameExists() {
        thrown.expect(BadRequestException.class);
        thrown.expectMessage("database config already exists with name");
        DatabaseConfig configToRegister = new DatabaseConfig();
        when(clock.getCurrentTimeMillis()).thenReturn(CURRENT_TIME_MILLIS);
        when(crnService.createDatabaseCrn()).thenReturn(CrnServiceTest.getValidCrn());
        when(databaseConfigRepository.save(configToRegister)).thenThrow(getDataIntegrityException());

        underTest.register(configToRegister);
    }

    private DataIntegrityViolationException getDataIntegrityException() {
        return new DataIntegrityViolationException("", new ConstraintViolationException("", new SQLException(), ""));
    }

    @Test
    public void testRegisterHasNoAccess() {
        thrown.expect(AccessDeniedException.class);
        DatabaseConfig configToRegister = new DatabaseConfig();
        when(clock.getCurrentTimeMillis()).thenReturn(CURRENT_TIME_MILLIS);
        when(crnService.createDatabaseCrn()).thenReturn(CrnServiceTest.getValidCrn());
        when(databaseConfigRepository.save(configToRegister)).thenThrow(new AccessDeniedException("User has no right to access resource"));

        underTest.register(configToRegister);
    }

    private void setupTransactionServiceRequired() throws TransactionExecutionException {
        when(transactionService.required(any())).thenAnswer((Answer<DatabaseConfig>) invocation -> {
            Supplier<DatabaseConfig> supplier = invocation.getArgument(0, Supplier.class);
            return supplier.get();
        });
    }
}