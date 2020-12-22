package com.sx.spring.exetend;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;
/**
 * 
 * 
 * <p>
 * Title: ExtendedPropertyPlaceholderConfigurer
 * </p>
 * <p>
 * Description: 扩展Spring属性管理配置
 * </p>
 * <p>
 * Company: bksx
 * </p>
 * 
 * @author 殷龙飞
 * @version 1.0
 */
public class ExtendedPropertyPlaceholderConfigurer extends PropertyPlaceholderConfigurer {
	private Properties props;

	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props)
			throws BeansException {
		super.processProperties(beanFactory, props);
		this.props = props;
	}

	public String getProperty(String key) {
		return props.getProperty(key);
	}
}