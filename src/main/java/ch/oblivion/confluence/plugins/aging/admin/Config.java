package ch.oblivion.confluence.plugins.aging.admin;

import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;
import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

@XmlRootElement
  @XmlAccessorType(XmlAccessType.FIELD)
  public final class Config
  {
    @XmlElement private int periodAmount;
    @XmlElement private int periodType;
    @XmlElement private String ignoreLabel;
    @XmlElement private int useMaster;
    
    private String spaceKey;
    
    public int getUseMaster() {
		return useMaster;
	}
    
    public void setUseMaster(int useMaster) {
		this.useMaster = useMaster;
	}
    
    public String getSpaceKey() {
		return spaceKey;
	}
    
    public void setSpaceKey(String spaceKey) {
		this.spaceKey = spaceKey;
	}
    
	public int getPeriodAmount()
    {
      return periodAmount;
    }
          
    public void setPeriodAmount(int periodAmount)
    {
      this.periodAmount = periodAmount;
    }
          
    public int getPeriodType()
    {
      return periodType;
    }
          
    public void setPeriodType(int periodType)
    {
      this.periodType = periodType;
    }
    
    public String getIgnoreLabel() {
		return ignoreLabel;
	}
    
    public void setIgnoreLabel(String ignoreLabel) {
		this.ignoreLabel = ignoreLabel;
	}
    
    @Override
    public String toString() {
    	StringBuilder builder = new StringBuilder();
    	builder.append(getClass().getCanonicalName());
    	builder.append(" : PeriodType = ");
    	builder.append(periodType);
    	builder.append(" : PeriodAmount = ");
    	builder.append(periodAmount);
    	builder.append(" : IgnoreLabel = ");
    	builder.append(ignoreLabel);
    	builder.append(" : SpaceKey = ");
    	builder.append(spaceKey);
    	builder.append(" : UseMaster = ");
    	builder.append(useMaster);
    	return builder.toString();
    }
  }