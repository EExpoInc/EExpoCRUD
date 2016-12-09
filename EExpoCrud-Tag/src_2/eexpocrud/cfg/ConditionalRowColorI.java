package eexpocrud.cfg;

import java.io.Serializable;

import eexpocrud.cfg.EExpoButtonCfg.BootstrapDefaultColor;

public interface ConditionalRowColorI extends Serializable {
	BootstrapDefaultColor execute();	
}
