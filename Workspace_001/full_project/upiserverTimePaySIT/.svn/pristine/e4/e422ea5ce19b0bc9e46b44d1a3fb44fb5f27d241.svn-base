package com.npst.upiserver.util;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.npst.upiserver.entity.CustomerMandatesEntity;
import com.npst.upiserver.entity.MandatesEntity;
import com.npst.upiserver.npcischema.RecurrencePatternType;
import com.npst.upiserver.npcischema.RecurrenceRuleType;

public class MandateUtil {
	private static final Logger log = LoggerFactory.getLogger(MandateUtil.class);
	private static final DateTimeFormatter formatter_ddMMyyyy = DateTimeFormatter.ofPattern("ddMMyyyy");

	public static boolean checkForTxnDayAndRecPattern(CustomerMandatesEntity mandates) {
		LocalDate today = LocalDate.now();
		String validitystart = mandates.getMandateValidityStart();
		LocalDate validitystartdate = LocalDate.parse(validitystart, formatter_ddMMyyyy);
		String recurrencepattern = mandates.getMandateRecurrencepattern();
		String recurrenceRuleType = mandates.getMandateRecurrenceRuletype();
		LocalDate dpattern = validitystartdate;
		if (RecurrencePatternType.ONETIME.toString().equalsIgnoreCase(recurrencepattern)) {
			return true;
		} else if (RecurrencePatternType.DAILY.toString().equalsIgnoreCase(recurrencepattern)) {
			return true;
		} else if (RecurrencePatternType.WEEKLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusDays(7);
			}

		} else if (RecurrencePatternType.FORTNIGHTLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusDays(15);
			}

		} else if (RecurrencePatternType.MONTHLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusMonths(1);
			}

		} else if (RecurrencePatternType.BIMONTHLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusMonths(2);
			}

		} else if (RecurrencePatternType.QUARTERLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusMonths(3);
			}

		} else if (RecurrencePatternType.HALFYEARLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusMonths(6);
			}

		} else if (RecurrencePatternType.YEARLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusYears(1);
			}

		}

		if (RecurrenceRuleType.AFTER.toString().equals(recurrenceRuleType)) {
			dpattern = dpattern.plusDays(1);
		}
		if (RecurrenceRuleType.BEFORE.toString().equals(recurrenceRuleType)) {
			dpattern = dpattern.minusDays(1);
		}

		return dpattern.isEqual(today);
	}

	public static boolean checkForTxnDayAndRecPattern(MandatesEntity mandates) {
        boolean flag =true;
		LocalDateTime today = LocalDateTime.now();
		String validitystart = mandates.getMandateValidityEnd();

		LocalDateTime validitystartdate = LocalDateTime.parse(validitystart, formatter_ddMMyyyy);
		String recurrencepattern = mandates.getMandateRecurrencepattern();

		LocalDateTime dpattern = validitystartdate;

		if (RecurrencePatternType.ONETIME.toString().equalsIgnoreCase(recurrencepattern)) {
			return true;
		} else if (RecurrencePatternType.DAILY.toString().equalsIgnoreCase(recurrencepattern)) {
			return true;
		} else if (RecurrencePatternType.WEEKLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusDays(7);
			}

		} else if (RecurrencePatternType.FORTNIGHTLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusDays(15);
			}

		} else if (RecurrencePatternType.MONTHLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusMonths(1);
			}

		} else if (RecurrencePatternType.BIMONTHLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusMonths(2);
			}

		} else if (RecurrencePatternType.QUARTERLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusMonths(3);
			}

		} else if (RecurrencePatternType.HALFYEARLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusMonths(6);
			}

		} else if (RecurrencePatternType.YEARLY.toString().equalsIgnoreCase(recurrencepattern)) {

			while (dpattern.isBefore(today)) {
				dpattern = validitystartdate.plusYears(1);
			}

		}
		//return dpattern.isEqual(today);
		return flag;
	}
}
