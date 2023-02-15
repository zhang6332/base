package com.ilsmp.base.dto;

import java.util.List;

import com.github.mars05.crud.hub.common.dto.FileTemplateDTO;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class ProjectTemplateRespDTO {
    private Long id;
    private String name;
    private String description;
    private Integer projectType;
    public List<FileTemplateDTO> fileTemplateList;
    private String createTime;
    private String updateTime;
    private Long createId;
    private String createName;
    private Long updateId;
    private String updateName;
    private Long organizationId;
    private String organizationName;
    private String accessToken;
    private Integer publicFlag;
}
