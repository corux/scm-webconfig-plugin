package de.corux.scm.plugins.webconfig;

import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import java.io.File;
import java.io.IOException;

import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TemporaryFolder;

import sonia.scm.repository.AbstractSimpleRepositoryHandler;
import sonia.scm.repository.Repository;
import sonia.scm.repository.SimpleRepositoryConfig;

public abstract class RepoConfigTestBase
{
    @Rule
    public TemporaryFolder repo = new TemporaryFolder();
    protected Repository repository;
    protected AbstractSimpleRepositoryHandler<? extends SimpleRepositoryConfig> handler;

    protected abstract Class<? extends AbstractSimpleRepositoryHandler<?>> getHandlerType();

    @Before
    public void initBase() throws IOException
    {
        repo.newFile("config");

        repository = mock(Repository.class);
        handler = mock(getHandlerType());
        File directory = repo.getRoot();
        when(handler.getDirectory(repository)).thenReturn(directory);
        when(repository.getName()).thenReturn("repo");
    }
}
