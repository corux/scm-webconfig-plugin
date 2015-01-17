package de.corux.scm.plugins.webconfig;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sonia.scm.repository.AbstractSimpleRepositoryHandler;
import sonia.scm.repository.SvnRepositoryHandler;

public class SvnRepoConfigTest extends RepoConfigTestBase
{
    @Before
    public void init() throws IOException
    {
        repo.newFolder("conf");
    }

    @Test
    public void testGetSetConfig() throws IOException
    {
        // arrange
        SvnRepoConfig repoConfig = new SvnRepoConfig((SvnRepositoryHandler) handler);
        String content = "[general]\n# anon-access = read";

        // act
        repoConfig.setConfig(repository, content);
        String config = repoConfig.getConfig(repository);

        // assert
        assertEquals(content, config);
    }

    @Override
    protected Class<? extends AbstractSimpleRepositoryHandler<?>> getHandlerType()
    {
        return SvnRepositoryHandler.class;
    }
}
