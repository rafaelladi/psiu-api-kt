package com.dietrich.psiuapikt.service.org

import com.dietrich.psiuapikt.controller.org.req.OrgProjectRequest
import com.dietrich.psiuapikt.controller.org.req.ProjectUpdateRequest
import com.dietrich.psiuapikt.exception.NotFoundException
import com.dietrich.psiuapikt.model.org.Project
import com.dietrich.psiuapikt.model.org.ProjectType
import com.dietrich.psiuapikt.repository.org.ProjectRepository
import com.dietrich.psiuapikt.service.util.MergeService
import org.springframework.data.repository.findByIdOrNull
import org.springframework.stereotype.Service

@Service
class ProjectService(
    val orgService: OrgService,
    val projectRepository: ProjectRepository,
    val mergeService: MergeService
) {
    fun findByOrg(id: Long): List<Project> {
        orgService.exists(id)
        return projectRepository.findByOrgId(id)
    }

    fun registerProject(id: Long, request: OrgProjectRequest): Project {
        val org = orgService.find(id)

        val project = Project(
            request.name,
            request.description,
            ProjectType.ON_DEMAND,
            org
        )
        projectRepository.saveAndFlush(project)

        return project
    }

    fun findAll(): List<Project> {
        return projectRepository.findAll()
    }

    fun find(id: Long): Project {
        return projectRepository.findByIdOrNull(id) ?: throw NotFoundException("Project", "id", id)
    }

    fun update(id: Long, request: ProjectUpdateRequest): Project {
        val project = find(id)
        mergeService.merge(request, project)

        return projectRepository.save(project)
    }

    fun exists(id: Long) {
        if(!projectRepository.existsById(id))
            throw NotFoundException("Project", "id", id)
    }

    fun delete(id: Long) {
        val project = find(id)
        project.active = false
        projectRepository.save(project)
    }
}