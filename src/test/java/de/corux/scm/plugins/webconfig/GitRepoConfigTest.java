package de.corux.scm.plugins.webconfig;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Test;

import sonia.scm.repository.AbstractSimpleRepositoryHandler;
import sonia.scm.repository.GitRepositoryHandler;

public class GitRepoConfigTest extends RepoConfigTestBase
{
    @Test
    public void testGetSetConfig() throws IOException
    {
        // arrange
        GitRepoConfig repoConfig = new GitRepoConfig((GitRepositoryHandler) handler);
        String content = "[core]\nsymlinks = false";

        // act
        repoConfig.setConfig(repository, content);
        String config = repoConfig.getConfig(repository);

        // assert
        assertEquals(content, config);
    }

    @Override
    protected Class<? extends AbstractSimpleRepositoryHandler<?>> getHandlerType()
    {
        return GitRepositoryHandler.class;
    }
}
