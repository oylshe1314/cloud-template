package com.sk.op.application.admin.dto;

import com.sk.op.application.entity.entity.Application;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
@Schema(description = "应用信息")
public class ApplicationDto {

    @Schema(description = "ID")
    private Long id;

    @Schema(description = "名称")
    private String name;

    @Schema(description = "环境")
    private String profile;

    @Schema(description = "标签")
    private String label;

    @Schema(description = "状态, 0: 禁用, 1: 启用")
    private Integer state;

    @Schema(description = "备注")
    private String remark;

    @Schema(description = "创建人")
    private String createBy;

    @Schema(description = "创建时间")
    private LocalDateTime createTime;

    @Schema(description = "更新人")
    private String updateBy;

    @Schema(description = "更新时间")
    private LocalDateTime updateTime;

    public ApplicationDto(Application application) {
        this(
                application.getId(),
                application.getName(),
                application.getProfile(),
                application.getLabel(),
                application.getState(),
                application.getRemark(),
                application.getCreateBy(),
                application.getCreateTime(),
                application.getUpdateBy(),
                application.getUpdateTime()
        );
    }
}
