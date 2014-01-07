/**
 * Project: activity-service File Created at 2011-5-10 $Id$ Copyright 2010 dianping.com. All rights reserved. This
 * software is the confidential and proprietary information of Dianping Company. ("Confidential Information"). You shall
 * not disclose such Confidential Information and shall use it only in accordance with the terms of the license
 * agreement you entered into with dianping.com.
 */
package com.dianping.activityservice.test.base;

import org.junit.runner.RunWith;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

/**
 * TODO Comment of AbstractTestWithRollBack
 * 
 * @author inter12
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "classpath*:/config/spring/*-*.xml" })
public abstract class AbstractTestWithRollBack {

}
