package com.sequenceiq.redbeams.service.dbconfig;

import java.util.Optional;

import javax.inject.Inject;

import org.hibernate.exception.ConstraintViolationException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import com.sequenceiq.cloudbreak.api.endpoint.v4.common.ResourceStatus;
import com.sequenceiq.cloudbreak.common.archive.AbstractArchivistService;
import com.sequenceiq.cloudbreak.common.service.Clock;
import com.sequenceiq.cloudbreak.common.service.TransactionService;
import com.sequenceiq.cloudbreak.common.service.TransactionService.TransactionExecutionException;
import com.sequenceiq.cloudbreak.exception.BadRequestException;
import com.sequenceiq.cloudbreak.exception.NotFoundException;
import com.sequenceiq.cloudbreak.logger.MDCBuilder;
import com.sequenceiq.redbeams.domain.DatabaseConfig;
import com.sequenceiq.redbeams.repository.DatabaseConfigRepository;
import com.sequenceiq.redbeams.service.crn.CrnService;

@Service
public class DatabaseConfigService extends AbstractArchivistService<DatabaseConfig> {

    private static final Logger LOGGER = LoggerFactory.getLogger(DatabaseConfigService.class);

    @Inject
    private DatabaseConfigRepository databaseConfigRepository;

    @Inject
    private Clock clock;

    @Inject
    private CrnService crnService;

    @Inject
    private TransactionService transactionService;

    public DatabaseConfig register(DatabaseConfig configToSave) {
        try {
            MDCBuilder.buildMdcContext(configToSave);
            // prepareCreation(configToSave);
            configToSave.setStatus(ResourceStatus.USER_MANAGED);
            configToSave.setCreationDate(clock.getCurrentTimeMillis());
            configToSave.setCrn(crnService.createDatabaseCrn());
            return databaseConfigRepository.save(configToSave);
        } catch (AccessDeniedException | DataIntegrityViolationException e) {
            ConstraintViolationException cve = null;
            for (Throwable t = e.getCause(); t != null; t = t.getCause()) {
                if (t instanceof ConstraintViolationException) {
                    cve = (ConstraintViolationException) t;
                    break;
                }
            }
            if (cve != null) {
                String message = String.format("database config already exists with name '%s'", configToSave.getName());
                throw new BadRequestException(message, cve);
            }
            throw e;
        }
    }

    public DatabaseConfig delete(String name) {
        try {
            return transactionService.required(() -> {
                Optional<DatabaseConfig> foundDatabaseConfig = databaseConfigRepository.findByName(name);
                DatabaseConfig databaseConfig = foundDatabaseConfig
                        .orElseThrow(() -> new NotFoundException(String.format("Database with name '%s' not found", name)));
                if (databaseConfig.isUserManaged()) {
                    deleteDatabase(databaseConfig);
                }
                delete(databaseConfig);
                return databaseConfig;
            });
        } catch (TransactionExecutionException e) {
            Throwable cause = e.getCause();
            if (cause instanceof NotFoundException) {
                throw (NotFoundException) cause;
            }
            LOGGER.warn("Deleting of DatabaseConfig failed: ", e);
            throw new RuntimeException("transaction went fail");
        }
    }

    private void deleteDatabase(DatabaseConfig databaseConfig) {

    }

    @Override
    public JpaRepository repository() {
        return databaseConfigRepository;
    }
}
