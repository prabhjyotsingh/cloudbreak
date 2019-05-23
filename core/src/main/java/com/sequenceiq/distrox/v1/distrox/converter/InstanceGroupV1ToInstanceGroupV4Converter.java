package com.sequenceiq.distrox.v1.distrox.converter;

import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.instancegroup.InstanceGroupV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.stacks.request.instancegroup.securitygroup.SecurityGroupV4Request;
import com.sequenceiq.cloudbreak.api.endpoint.v4.util.requests.SecurityRuleV4Request;
import com.sequenceiq.distrox.api.v1.distrox.model.instancegroup.InstanceGroupV1Request;

public class InstanceGroupV1ToInstanceGroupV4Converter {

    public InstanceGroupV4Request convert(InstanceGroupV1Request source) {
        InstanceGroupV4Request response = new InstanceGroupV4Request();
        response.setNodeCount(source.getNodeCount());
        response.setType(source.getType());
        response.setCloudPlatform(source.getC);
        response.setName(source.getName());
        response.setTemplate(source.getTemplate());
        response.setRecoveryMode(source.getRecoveryMode());
        response.setSecurityGroup(getSecurotyGroup());
        response.setRecipeNames();
        response.setAws(source.getAws());
        return response;
    }

    private SecurityGroupV4Request getSecurotyGroup() {
        SecurityGroupV4Request response = new SecurityGroupV4Request();
        SecurityRuleV4Request securityRule = new SecurityRuleV4Request();
        securityRule.setPorts(List.of("22", "443"));
        securityRule.setProtocol("tcp");
        securityRule.setSubnet("0.0.0.0/0");
        response.setSecurityRules(List.of(securityRule));
        return response;
    }

    public List<InstanceGroupV4Request> convert(Set<InstanceGroupV1Request> instanceGroups) {
        return instanceGroups.stream().map(this::convert).collect(Collectors.toList());
    }
}
