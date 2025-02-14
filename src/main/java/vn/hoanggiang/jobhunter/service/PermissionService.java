package vn.hoanggiang.jobhunter.service;

import java.util.Optional;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

import vn.hoanggiang.jobhunter.domain.Permission;
import vn.hoanggiang.jobhunter.domain.response.ResultPaginationDTO;
import vn.hoanggiang.jobhunter.repository.PermissionRepository;

@Service
@Slf4j
public class PermissionService {
    private final PermissionRepository permissionRepository;

    public PermissionService(PermissionRepository permissionRepository) {
        this.permissionRepository = permissionRepository;
    }

    public boolean isPermissionExist(Permission permission) {
        return permissionRepository.existsByModuleAndApiPathAndMethod(
                permission.getModule(),
                permission.getApiPath(),
                permission.getMethod());
    }

    public Permission fetchById(long id) {
        Optional<Permission> permissionOptional = this.permissionRepository.findById(id);
        log.info("Fetch permission with {} successfully", id);
        return permissionOptional.orElse(null);
    }

    public Permission createPermission(Permission permission) {
        log.info("Create permission {} successfully", permission.getName());
        return this.permissionRepository.save(permission);
    }

    public Permission updatePermission(Permission permission) {
        Permission permissionDB = this.fetchById(permission.getId());
        if (permissionDB != null) {
            permissionDB.setName(permission.getName());
            permissionDB.setApiPath(permission.getApiPath());
            permissionDB.setMethod(permission.getMethod());
            permissionDB.setModule(permission.getModule());

            // update permission
            permissionDB = this.permissionRepository.save(permissionDB);
            log.info("Update permission {} successfully", permission.getName());
        }
        return permissionDB;
    }

    public void deletePermission(long id) {
        // find permission by id
        Permission permission = this.permissionRepository.findById(id).orElse(null);

        if (permission != null) {
            // delete permission_role
            permission.getRoles().forEach(role -> role.getPermissions().remove(permission));
            // delete permission
            log.info("Delete permission with {} successfully", permission.getId());
            this.permissionRepository.delete(permission);
        }
    }

    public ResultPaginationDTO getPermissions(Specification<Permission> spec, Pageable pageable) {
        Page<Permission> pPermissions = this.permissionRepository.findAll(spec, pageable);

        ResultPaginationDTO rs = new ResultPaginationDTO();
        ResultPaginationDTO.Meta mt = new ResultPaginationDTO.Meta();

        mt.setPage(pageable.getPageNumber() + 1);
        mt.setPageSize(pageable.getPageSize());
        mt.setPages(pPermissions.getTotalPages());
        mt.setTotal(pPermissions.getTotalElements());

        rs.setMeta(mt);
        rs.setResult(pPermissions.getContent());

        log.info("Fetch all permissions successfully");
        return rs;
    }
}
