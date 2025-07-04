package br.tech.oe.plan.dto.group;

import br.tech.oe.plan.dto.BaseDTO;
import br.tech.oe.plan.dto.SimpleUserDTO;
import br.tech.oe.plan.enums.GroupVisibility;

import java.time.Instant;
import java.util.List;

public class GroupDTO extends BaseDTO {
    private SimpleUserDTO owner;
    private String title;
    private String description;
    private String code;
    private String bannerUrl;
    private List<String> tags;
    private String color;
    private String category;
    private GroupVisibility visibility;
    private Instant archivedAt;

    public SimpleUserDTO getOwner() {
        return owner;
    }

    public void setOwnerUuid(SimpleUserDTO owner) {
        this.owner = owner;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getBannerUrl() {
        return bannerUrl;
    }

    public void setBannerUrl(String bannerUrl) {
        this.bannerUrl = bannerUrl;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public GroupVisibility getVisibility() {
        return visibility;
    }

    public void setVisibility(GroupVisibility visibility) {
        this.visibility = visibility;
    }

    public Instant getArchivedAt() {
        return archivedAt;
    }

    public void setArchivedAt(Instant archivedAt) {
        this.archivedAt = archivedAt;
    }
}
