package br.tech.oe.plan.service;

import br.tech.oe.plan.dto.attachment.AttachmentDTO;
import br.tech.oe.plan.dto.attachment.CreateAttachmentDTO;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.UUID;

public interface AttachmentService {
    @Transactional
    AttachmentDTO uploadFile(MultipartFile file, CreateAttachmentDTO dto);

    @Transactional
    void deleteFile(UUID uuid);
}
