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

import java.util.HashSet;
import java.util.Set;

public class MonthDayRule implements BusinessDayRule {
	final int mm;
	final int dd;
	final int wd;
	
	public MonthDayRule(String[] args) {
		mm = Integer.parseInt(args[0]);
		dd = Integer.parseInt(args[1]);
		wd = Integer.parseInt(args[2]);
	}

	@Override
	public Set<Integer> getAll(int y) {
		Set<Integer> res = new HashSet<Integer>();
		res.add((mm<<5) + getDate(y));
		return res;
	}
	
	private int getDate(int y) {
		return ((wd + 7 - DateUtils.getWeekDay(y, mm, dd)) % 7) + dd;
	}
	
	@Override
	public boolean apply(int y, int m, int d) {
		return mm == m && getDate(y) == d;
	}

}
