package de.corux.scm.plugins.webconfig;

import sonia.scm.plugin.ext.Extension;

import com.google.inject.AbstractModule;

@Extension
public class WebConfigModule extends AbstractModule
{
    @Override
    protected void configure()
    {
        bind(GitRepoConfig.class);
        bind(HgRepoConfig.class);
        bind(SvnRepoConfig.class);
    }
}
