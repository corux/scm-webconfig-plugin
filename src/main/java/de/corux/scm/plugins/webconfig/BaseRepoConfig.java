package de.corux.scm.plugins.webconfig;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.StringReader;

import sonia.scm.repository.Repository;
import sonia.scm.util.IOUtil;

/**
 * Base class for retrieving and setting custom repository configurations.
 */
public abstract class BaseRepoConfig
{
    /**
     * Gets the config for the given repository.
     *
     * @param repository
     *            the repository
     * @return the config
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    protected abstract String getConfig(final Repository repository) throws IOException;

    /**
     * Sets the configuration for the given repository.
     *
     * @param repository
     *            the repository
     * @param content
     *            the content of the configuration.
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    protected abstract void setConfig(final Repository repository, final String content) throws IOException;

    /**
     * Reads the given file as a string.
     *
     * @param directory
     *            the directory
     * @param file
     *            the file
     * @return the string
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    protected String readFileAsString(final File directory, final String file) throws IOException
    {
        File fullPath = new File(directory.getAbsolutePath(), file);
        FileInputStream fileInputStream = new FileInputStream(fullPath);
        try
        {
            String content = IOUtil.getContent(fileInputStream);
            return content;
        }
        finally
        {
            IOUtil.close(fileInputStream);
        }
    }

    /**
     * Writes the given content to the specified file in the given directory.
     *
     * @param directory
     *            the directory
     * @param file
     *            the file
     * @param content
     *            the content
     * @throws IOException
     *             Signals that an I/O exception has occurred.
     */
    protected void writeContentToFile(final File directory, final String file, final String content) throws IOException
    {
        File fullPath = new File(directory.getAbsolutePath(), file);
        FileWriter fileWriter = new FileWriter(fullPath);
        StringReader stringReader = new StringReader(content);
        try
        {
            IOUtil.copy(stringReader, fileWriter);
        }
        finally
        {
            IOUtil.close(fileWriter);
            IOUtil.close(stringReader);
        }
    }
}
