package de.corux.scm.plugins.webconfig;

import static org.junit.Assert.assertEquals;

import java.io.IOException;

import org.junit.Before;
import org.junit.Test;

import sonia.scm.repository.AbstractSimpleRepositoryHandler;
import sonia.scm.repository.HgRepositoryHandler;

public class HgRepoConfigTest extends RepoConfigTestBase
{
    @Before
    public void init() throws IOException
    {
        repo.newFolder(".hg");
    }

    @Test
    public void testGetSetConfig() throws IOException
    {
        // arrange
        HgRepoConfig repoConfig = new HgRepoConfig((HgRepositoryHandler) handler);
        String content = "[web]\npush_ssl = false";

        // act
        repoConfig.setConfig(repository, content);
        String config = repoConfig.getConfig(repository);

        // assert
        assertEquals(content, config);
    }

    @Override
    protected Class<? extends AbstractSimpleRepositoryHandler<?>> getHandlerType()
    {
        return HgRepositoryHandler.class;
    }
}
