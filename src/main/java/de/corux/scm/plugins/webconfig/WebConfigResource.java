package de.corux.scm.plugins.webconfig;

import java.io.IOException;

import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.apache.commons.lang.StringUtils;

import sonia.scm.repository.Repository;
import sonia.scm.repository.RepositoryNotFoundException;
import sonia.scm.repository.api.RepositoryService;
import sonia.scm.repository.api.RepositoryServiceFactory;

import com.google.inject.Inject;

/**
 * Handles the retrieving and saving of repository configuration files.
 */
@Path("plugins/webconfig")
public class WebConfigResource
{
    private final RepositoryServiceFactory repositoryServiceFactory;
    private final GitRepoConfig gitConfig;
    private final HgRepoConfig hgConfig;
    private final SvnRepoConfig svnConfig;

    @Inject
    public WebConfigResource(final RepositoryServiceFactory repositoryServiceFactory, final GitRepoConfig gitConfig,
            final HgRepoConfig hgConfig, final SvnRepoConfig svnConfig)
    {
        this.repositoryServiceFactory = repositoryServiceFactory;
        this.gitConfig = gitConfig;
        this.hgConfig = hgConfig;
        this.svnConfig = svnConfig;
    }

    @GET
    @Produces({ MediaType.APPLICATION_JSON, MediaType.APPLICATION_XML })
    public Response getConfiguration(@QueryParam("repository-id") final String repositoryId)
    {
        Status status = Status.OK;
        try
        {
            Repository repository = getRepository(repositoryId);

            GetConfigResponse response = new GetConfigResponse();
            String content = null;
            String type = repository.getType();
            if ("git".equals(type))
            {
                content = gitConfig.getConfig(repository);
            }
            else if ("hg".equals(type))
            {
                content = hgConfig.getConfig(repository);
            }
            else if ("svn".equals(type))
            {
                content = svnConfig.getConfig(repository);
            }
            else
            {
                status = Status.CONFLICT;
            }

            if (content != null)
            {
                response.setSuccess(true);
                response.getData().put("content", content);
                return Response.ok(response).build();
            }
        }
        catch (RepositoryNotFoundException e)
        {
            status = Status.NOT_FOUND;
        }
        catch (IOException e)
        {
            status = Status.CONFLICT;
        }

        return Response.status(status).build();
    }

    @POST
    public Response setConfiguration(@FormParam("repository-id") final String repositoryId,
            @FormParam("content") final String content)
    {
        if (StringUtils.isBlank(content))
        {
            return Response.status(Status.CONFLICT).build();
        }

        Status status = Status.OK;
        try
        {
            Repository repository = getRepository(repositoryId);
            String type = repository.getType();
            if ("git".equals(type))
            {
                gitConfig.setConfig(repository, content);
            }
            else if ("hg".equals(type))
            {
                hgConfig.setConfig(repository, content);
            }
            else if ("svn".equals(type))
            {
                svnConfig.setConfig(repository, content);
            }
            else
            {
                status = Status.CONFLICT;
            }
        }
        catch (RepositoryNotFoundException e)
        {
            status = Status.NOT_FOUND;
        }
        catch (IOException e)
        {
            status = Status.CONFLICT;
        }

        return Response.status(status).build();
    }

    /**
     * Gets the repository by its id.
     *
     * @param repositoryId
     *            the repository id
     * @return the repository
     * @throws RepositoryNotFoundException
     *             the repository was not found
     */
    private Repository getRepository(final String repositoryId) throws RepositoryNotFoundException
    {
        RepositoryService repositoryService = null;
        try
        {
            repositoryService = repositoryServiceFactory.create(repositoryId);
            Repository repository = repositoryService.getRepository();
            return repository;
        }
        finally
        {
            if (repositoryService != null)
            {
                repositoryService.close();
            }
        }
    }
}
