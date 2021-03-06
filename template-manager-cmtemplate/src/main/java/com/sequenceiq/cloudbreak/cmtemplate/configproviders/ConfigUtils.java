package com.sequenceiq.cloudbreak.cmtemplate.configproviders;

import java.util.Optional;

import com.cloudera.api.swagger.model.ApiClusterTemplateConfig;
import com.cloudera.api.swagger.model.ApiClusterTemplateVariable;
import com.sequenceiq.cloudbreak.api.endpoint.v4.database.base.DatabaseType;
import com.sequenceiq.cloudbreak.domain.RDSConfig;
import com.sequenceiq.cloudbreak.template.TemplatePreparationObject;

public class ConfigUtils {

    private ConfigUtils() { }

    /**
     * Convenience method for creating an {@link ApiClusterTemplateConfig} with a value.
     *
     * @param name config name
     * @param value config value
     * @return config object
     */
    public static ApiClusterTemplateConfig config(String name, String value) {
        return new ApiClusterTemplateConfig().name(name).value(value);
    }

    /**
     * Convenience method for creating an {@link ApiClusterTemplateConfig} with a variable.
     *
     * @param name config name
     * @param variable config variable
     * @return config object
     */
    public static ApiClusterTemplateConfig configVar(String name, String variable) {
        return new ApiClusterTemplateConfig().name(name).variable(variable);
    }

    /**
     * Convenience method for creating an {@link ApiClusterTemplateVariable}.
     *
     * @param name variable name
     * @param value variable value
     * @return variable object
     */
    public static ApiClusterTemplateVariable variable(String name, String value) {
        return new ApiClusterTemplateVariable().name(name).value(value);
    }

    public static Optional<RDSConfig> getFirstRDSConfigOptional(TemplatePreparationObject source, DatabaseType databaseType) {
        return source.getRdsConfigs().stream()
                .filter(rds -> databaseType.name().equalsIgnoreCase(rds.getType()))
                .findFirst();
    }

}
