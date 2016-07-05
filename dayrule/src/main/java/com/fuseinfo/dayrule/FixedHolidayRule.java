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

public class FixedHolidayRule implements BusinessDayRule {
	final int mm;
	final int dd;
	final int extension;
	final Set<Integer> observe = new HashSet<>();

	public FixedHolidayRule(String[] args) {
		mm = Integer.parseInt(args[0]);
		dd = Integer.parseInt(args[1]);
		if (args.length > 2) {
			extension = Integer.parseInt(args[2]) - 1;
			for (int i = 3; i < args.length; i++) {
				observe.add(Integer.parseInt(args[i]));
			}
		} else {
			extension = 0;
		}
	}
	
	@Override
	public Set<Integer> getAll(int y) {
		final int range = getRange(y);
		final int base = mm << 5;
		Set<Integer> res = new HashSet<Integer>();
		for (int d = dd; d <= range; d++) {
			res.add(base + d);
		}
		return res;
	}
	
	private int getRange(int y) {
		final int size = observe.size();
		if (size > 0) {
			int base = this.extension;
			int ext = 0;
			int wd = DateUtils.getWeekDay(y, mm, dd);
			boolean contained;
			while ((contained = observe.contains(wd)) || base > 0) {
				if (!contained) base--;
				ext ++;
				wd = (wd+1) % 7;
			}
			return dd + base + ext;
		} else {
			return dd + extension;
		}
	}

	@Override
	public boolean apply(int y, int m, int d) {
		final int range = getRange(y);
		return (m == mm && d >=dd && d<= range);
	}

}
