package com.ruoyi.common.model;

import java.util.List;
import java.util.stream.Collectors;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.ruoyi.common.entity.SysDept;
import com.ruoyi.common.entity.SysMenu;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Getter;
import lombok.Setter;

/**
 * Treeselect树结构实体类
 *
 * @author ruoyi
 */
@Setter
@Getter
@Schema(description = "树")
public class TreeSelect {
    @Schema(description = "节点ID")
    private Long id;
    @Schema(description = "节点名称")
    private String label;
    @Schema(description = "子节点")
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<TreeSelect> children;

    public TreeSelect() {

    }

    public TreeSelect(SysDept dept) {
        this.id = dept.getDeptId();
        this.label = dept.getDeptName();
        this.children = dept.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

    public TreeSelect(SysMenu menu) {
        this.id = menu.getMenuId();
        this.label = menu.getMenuName();
        this.children = menu.getChildren().stream().map(TreeSelect::new).collect(Collectors.toList());
    }

}
