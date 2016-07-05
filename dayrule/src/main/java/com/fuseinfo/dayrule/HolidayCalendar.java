/**
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.fuseinfo.dayrule;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;


public class HolidayCalendar {
	final BusinessDayRule[] rules;
	
	public HolidayCalendar(final String[] confs) {
		final List<BusinessDayRule> ruleList = new ArrayList<BusinessDayRule>();
		for (final String conf: confs) {
			final int ind = conf.indexOf(',');
			try {
				final Object rule ;
				if (ind > 0) {
					final String className = conf.substring(0, ind).trim();
					final String[] args = conf.substring(ind + 1).trim().split("\\,");
					rule = Class.forName(className).getDeclaredConstructor(args.getClass()).newInstance(new Object[]{args});				
				} else {
					rule = Class.forName(conf.trim()).newInstance();
				}
				if (rule instanceof BusinessDayRule) {
					ruleList.add((BusinessDayRule) rule);
				}
			} catch (ClassNotFoundException | InstantiationException | IllegalAccessException | 
				IllegalArgumentException | InvocationTargetException | NoSuchMethodException | SecurityException e) {
				e.printStackTrace();
			}
		}
		rules = ruleList.toArray(new BusinessDayRule[ruleList.size()]);
	}
	
	public HolidayCalendar(final String confFile) {
		this(loadProps(confFile));		
	}
	
	public static String[] loadProps(final String confFile) {
		try (
			InputStream is = HolidayCalendar.class.getClassLoader().getResourceAsStream(confFile);
			BufferedReader br = new BufferedReader(new InputStreamReader(is, "UTF-8"))) {
			List<String> confList = new ArrayList<>();
			String line;
			while ((line = br.readLine()) != null) {
				String lineTrim = line.trim();
				if (lineTrim.length() > 0) {
					confList.add(lineTrim);
				}
			}
			return confList.toArray(new String[confList.size()]);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return new String[0];
	}

}
