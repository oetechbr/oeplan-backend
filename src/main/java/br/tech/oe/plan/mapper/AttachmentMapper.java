package br.tech.oe.plan.mapper;

import br.tech.oe.plan.dto.attachment.AttachmentDTO;
import br.tech.oe.plan.model.AttachmentModel;

public class AttachmentMapper {

    public static AttachmentDTO toDTO(AttachmentModel model) {
        var dto = new AttachmentDTO();
        dto.setUuid(model.getUuid());
        dto.setFileName(model.getFileName());
        dto.setDescription(model.getDescription());
        dto.setSecureUrl(model.getSecureUrl());
        dto.setPublicId(model.getPublicId());
        dto.setFormat(model.getFormat());
        dto.setBytes(model.getBytes());
        dto.setHeight(model.getHeight());
        dto.setWidth(model.getWidth());
        dto.setResourceType(model.getResourceType());
        dto.setCreatedAt(model.getCreatedAt());
        return dto;
    }
}
