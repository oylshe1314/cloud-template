package com.sk.op.application.admin.controller;

import com.sk.op.application.admin.dto.ApplicationDto;
import com.sk.op.application.admin.dto.QueryApplicationDto;
import com.sk.op.application.admin.service.ApplicationService;
import com.sk.op.application.common.dto.PageRequestDto;
import com.sk.op.application.common.dto.PageResultDto;
import com.sk.op.application.common.dto.ResponseDto;
import com.sk.op.application.entity.entity.Application;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@Validated
@RestController
@RequestMapping("/application")
@Tag(name = "application", description = "应用相关接口")
public class ApplicationController {

    private final ApplicationService applicationService;

    public ApplicationController(ApplicationService applicationService) {
        this.applicationService = applicationService;
    }

    @Operation(summary = "应用查询")
    @RequestMapping(value = "/query", method = RequestMethod.POST)
    public ResponseDto<PageResultDto<ApplicationDto>> query(@Validated @RequestBody PageRequestDto<QueryApplicationDto> pageRequest) throws Exception {
        Pageable pageable = PageRequest.of(pageRequest.getPageNo() - 1, pageRequest.getPageSize());
        Page<Application> page;
        if (pageRequest.getQuery() == null) {
            page = applicationService.query(pageable);
        } else {
            page = applicationService.query(pageable);
        }
        return ResponseDto.succeed(new PageResultDto<>(pageable, page, ApplicationDto::new));
    }
}
