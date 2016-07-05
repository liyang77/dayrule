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

public class DateUtils {
	private static final int[] t = new int[]{0,3,2,5,0,3,5,1,4,6,2,4};
	private static final int[] s = new int[]{31,28,31,30,31,30,31,31,30,31,30,31};

	public static int getWeekDay(final int y, final int m, final int d) {
		final int yy = m < 3? y - 1:y;
		return (yy + yy/4 - yy/100 + yy/400 + t[m - 1] + d) % 7;
	}
	
	public static int lastDay (final int y, final int m) {
		return m!=2?s[m - 1]:(y%4==0 && (y%100!=0 || y%400 ==0))?29:28;
	}
	
	public static int[] adjDays(int y, int m, int d) {
		if (d <= 0) {
			if ((--m) < 1) {
				y--;
				m += 12;
			}
			d += lastDay(y, m);
			return adjDays(y, m, d);
		} else {
			int ld = lastDay(y, m);
			if ( d > ld) {
				d -= ld;
				if ((++m) > 12) {
					y++;
					m -= 12;
				}
				return adjDays(y, m, d);
			} else {
				return new int[]{y, m, d};
			}
		}
	}
	
}
