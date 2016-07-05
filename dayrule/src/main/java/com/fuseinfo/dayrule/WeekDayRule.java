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

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public class WeekDayRule implements BusinessDayRule {
	private int[] weekdays;

	public WeekDayRule(String[] args) {
		List<Integer> wdList = new ArrayList<>();
		for (final String arg: args) {
			try {
				wdList.add(Integer.parseInt(arg.trim()));
			} catch (NumberFormatException e) {
				e.printStackTrace();
			}
		}
		weekdays = new int[wdList.size()];
		Iterator<Integer> itWd = wdList.iterator();
		for (int i = 0; i < weekdays.length; i++) {
			weekdays[i] = itWd.next();
		}
	}

	@Override
	public boolean apply(final int y, final int m, final int d) {
		final int wd = DateUtils.getWeekDay(y, m, d);
		for (final int weekday: weekdays) {
			if (weekday == wd) {
				return true;
			}
		}
		return false;
	}

	@Override
	public Set<Integer> getAll(int y) {
		final int wd = DateUtils.getWeekDay(y, 1, 1);
		Set<Integer> allDays = new HashSet<>();
		for (final int weekday: weekdays) {
			int[] ymd = new int[]{y, 1, (weekday + 7 - wd) % 7 + 1};
			do {
				allDays.add((ymd[1] << 5) + ymd[2]);
				ymd = DateUtils.adjDays(ymd[0], ymd[1], ymd[2] + 7);
			} while (ymd[0] == y);
		}
		return allDays;
	}

}
