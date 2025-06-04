package br.tech.oe.plan.service.impl;

import br.tech.oe.plan.dto.attachment.AttachmentDTO;
import br.tech.oe.plan.dto.attachment.CreateAttachmentDTO;
import br.tech.oe.plan.exception.InternalServerErrorException;
import br.tech.oe.plan.exception.ItemNotFoundException;
import br.tech.oe.plan.mapper.AttachmentMapper;
import br.tech.oe.plan.model.AttachmentModel;
import br.tech.oe.plan.repository.AttachmentRepository;
import br.tech.oe.plan.service.AttachmentService;
import com.cloudinary.Cloudinary;
import com.cloudinary.utils.ObjectUtils;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.util.Map;
import java.util.UUID;

@Service
@SuppressWarnings("rawtypes")
public class AttachmentServiceImpl implements AttachmentService {

    private final Cloudinary cloudinary;

    private final ObjectMapper objectMapper;

    private final AttachmentRepository attachmentRepository;

    public AttachmentServiceImpl(
            ObjectMapper objectMapper, Cloudinary cloudinary,
            AttachmentRepository attachmentRepository
    ) {
        this.objectMapper = objectMapper;
        this.cloudinary = cloudinary;
        this.attachmentRepository = attachmentRepository;
    }

    @Override
    public AttachmentDTO uploadFile(MultipartFile file, CreateAttachmentDTO dto) {
        try {
            Map uploadResult = cloudinary.uploader().upload(
                    file.getBytes(),
                    ObjectUtils.emptyMap()
            );

            var attachment = objectMapper.convertValue(uploadResult, AttachmentModel.class);
            attachment.setFileName(dto.getFileName());
            attachment.setDescription(dto.getDescription());

            if (attachment.getFileName() == null) attachment.setFileName(UUID.randomUUID().toString());
            var savedAttachment = attachmentRepository.save(attachment);

            return AttachmentMapper.toDTO(savedAttachment);
        } catch (Exception e) {
            throw new InternalServerErrorException("Couldn't upload file");
        }
    }

    @Override
    public void deleteFile(UUID uuid) {
        var file = attachmentRepository.findById(uuid).orElseThrow(
                () -> new ItemNotFoundException("File doesn't exist")
        );

        try {
            cloudinary.uploader().destroy(file.getPublicId(), ObjectUtils.emptyMap());
            attachmentRepository.deleteById(uuid);
        } catch (Exception e) {
            throw new InternalServerErrorException("Couldn't delete file");
        }
    }
}
