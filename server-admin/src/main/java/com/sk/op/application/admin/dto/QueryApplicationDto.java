package com.sk.op.application.admin.dto;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.sk.op.application.common.validation.In;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import org.hibernate.validator.constraints.Length;

import javax.validation.constraints.Positive;

@Getter
@Schema(description = "应用查询参数")
public class QueryApplicationDto {

    @Schema(description = "ID")
    @Positive(message = "ID不正确")
    private final Long id;

    @Schema(description = "名称")
    @Length(min = 1, message = "不能为空")
    private final String name;

    @Schema(description = "环境")
    @Length(min = 1, message = "不能为空")
    private final String profile;

    @Schema(description = "标签")
    @Length(min = 1, message = "不能为空")
    private final String label;

    @Schema(description = "状态")
    @In(values = {0, 1}, notNull = false, message = "状态不正确")
    private final Integer state;

    @JsonCreator
    public QueryApplicationDto(@JsonProperty("id") Long id, @JsonProperty("name") String name, @JsonProperty("profile") String profile, @JsonProperty("label") String label, @JsonProperty("state") Integer state) {
        this.id = id;
        this.name = name;
        this.profile = profile;
        this.label = label;
        this.state = state;
    }
}
