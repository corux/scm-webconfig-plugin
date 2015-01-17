package de.corux.scm.plugins.webconfig;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import sonia.scm.repository.Repository;
import sonia.scm.repository.SvnRepositoryHandler;

/**
 * Subversion repository configuration.
 */
public class SvnRepoConfig extends BaseRepoConfig
{
    private static final String SVN_CONFIG = "conf".concat(File.separator).concat("svnserve.conf");
    private final SvnRepositoryHandler svnHandler;

    @Inject
    public SvnRepoConfig(final SvnRepositoryHandler svnHandler)
    {
        this.svnHandler = svnHandler;
    }

    @Override
    protected String getConfig(final Repository repository) throws IOException
    {
        File directory = svnHandler.getDirectory(repository);
        return readFileAsString(directory, SVN_CONFIG);
    }

    @Override
    protected void setConfig(final Repository repository, final String content) throws IOException
    {
        File directory = svnHandler.getDirectory(repository);
        writeContentToFile(directory, SVN_CONFIG, content);
    }
}
