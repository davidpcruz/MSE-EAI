package eai.msejdf.admin;

import javax.ejb.LocalBean;
import javax.ejb.Stateful;

import eai.msejdf.admin.IAdmin;


//TODO-fjn: Check whenever we'll keep this or have it distributed in other beans with method level security permissions
/**
 * Bean implementing interface for administration related calls
 */
@Stateful
@LocalBean
public class Admin implements IAdmin{

}
