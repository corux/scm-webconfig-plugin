package de.corux.scm.plugins.webconfig;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import sonia.scm.repository.GitRepositoryHandler;
import sonia.scm.repository.Repository;

/**
 * Git repository configuration.
 */
public class GitRepoConfig extends BaseRepoConfig
{
    private static final String GIT_CONFIG = "config";
    private final GitRepositoryHandler gitHandler;

    @Inject
    public GitRepoConfig(final GitRepositoryHandler gitHandler)
    {
        this.gitHandler = gitHandler;
    }

    @Override
    protected String getConfig(final Repository repository) throws IOException
    {
        File directory = gitHandler.getDirectory(repository);
        return readFileAsString(directory, GIT_CONFIG);
    }

    @Override
    protected void setConfig(final Repository repository, final String content) throws IOException
    {
        File directory = gitHandler.getDirectory(repository);
        writeContentToFile(directory, GIT_CONFIG, content);
    }
}
