package lk.avsec_welfare.asset.employee.entity.enums;

import lombok.AllArgsConstructor;
import lombok.Getter;


@Getter
@AllArgsConstructor
public enum UniformType {

    UF("Uniform Staff"),
    CL("Civil Staff");

    private final String uniformType;
}
