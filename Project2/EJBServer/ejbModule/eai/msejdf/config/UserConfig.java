package eai.msejdf.config;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import eai.msejdf.config.IUserConfig;


/**
 * Bean implementing interface for user information related configuration calls
 */
@Stateful
@LocalBean
public class UserConfig implements IUserConfig{

}
