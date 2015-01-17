package de.corux.scm.plugins.webconfig;

import java.io.File;
import java.io.IOException;

import javax.inject.Inject;

import sonia.scm.repository.HgRepositoryHandler;
import sonia.scm.repository.Repository;

/**
 * Mercurial repository configuration.
 */
public class HgRepoConfig extends BaseRepoConfig
{
    private static final String HG_CONFIG = HgRepositoryHandler.PATH_HGRC;
    private final HgRepositoryHandler hgHandler;

    @Inject
    public HgRepoConfig(final HgRepositoryHandler hgHandler)
    {
        this.hgHandler = hgHandler;
    }

    @Override
    protected String getConfig(final Repository repository) throws IOException
    {
        File directory = hgHandler.getDirectory(repository);
        return readFileAsString(directory, HG_CONFIG);
    }

    @Override
    protected void setConfig(final Repository repository, final String content) throws IOException
    {
        File directory = hgHandler.getDirectory(repository);
        writeContentToFile(directory, HG_CONFIG, content);
    }
}
