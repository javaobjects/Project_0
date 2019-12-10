package com.kingdee.eas.st.common.util;

import java.util.HashMap;
import java.util.Map;

/**
 * ����: ͨ�ó�������
 * 
 * @author Brina date:2006-11-29
 *         <p>
 * @version EAS5.2
 */
public abstract class WeighTypeConstant {

	/* ������ͱ��� */
	public static final String WEIGH_TYPE_OTHERWEIGH = "STW000"; // ����������

	public static final String WEIGH_TYPE_PURINCEPT = "STW001"; // �ɹ��ջ�����

	public static final String WEIGH_TYPE_PURSEND = "STW002"; // �ɹ��˻�����

	public static final String WEIGH_TYPE_SALESEND = "STW003"; // ���۷�������

	public static final String WEIGH_TYPE_SALEINCEPT = "STW004"; // �����˻ر���

	public static final String WEIGH_TYPE_IRONTRANS = "STW005"; // ��ˮ��������

	public static final String WEIGH_TYPE_STEELTRANS = "STW006"; // ������������

	public static final String WEIGH_TYPE_OTHERTRANS = "STW007"; // ������������

	public static final String WEIGH_TYPE_MATERIALTRANS = "STW008"; // ����ԭȼ�ϵ�������

	public static final String WEIGH_TYPE_TEMPDEPOSITED = "STW009"; // �����������

	public static final String WEIGH_TYPE_CHECKUP = "STW010"; // �����ü�����

	public static final String WEIGH_TYPE_FLOTSAMCALLBACK = "STW011"; // ���ϻ��ռ�����

	public static final String WEIGH_TYPE_COMPLETEINCOME = "STW012"; // ��Ʒ��������

	// ������:PT013466,�ᵥ��:R080416-078���������ּ������ ��־ΰ
	public static final String WEIGH_TYPE_DELEGATEIN = "STW013"; //ί��ӹ���⣬������λӦΪ
																	// ����Ӧ�̡���
																	// �ջ���λӦΪ
																	// ��������֯����
	public static final String WEIGH_TYPE_DELEGATEOUT = "STW014"; //ί��ӹ����⣬������λӦΪ
																	// ��������֯����
																	// �ջ���λӦΪ
																	// ����Ӧ�̡���

	// add by fanmm at 2011-4-14 for ������Ŀ
	public static final String WEIGH_TYPE_IRONTRANS_IN = "STW025"; // �����������
	public static final String WEIGH_TYPE_MATERIALTRANS_IN = "STW028"; // �ڲ����������
	// add end 2011-4-14
	// add by fanmm at 2011-4-14 for ������Ŀ
	public static final String WEIGH_TYPE_IRONTRANS_INID = "EGXnNx+DT7WJMXSth0S62Ogkqv4="; // �����������
	public static final String WEIGH_TYPE_MATERIALTRANS_INID = "SPQpkF1yQYa+fu0SqJOTiegkqv4="; // �ڲ����������
	// add end 2011-4-14

	/* ����������� */
	public static final String WEIGH_TYPE_PURINCEPTID = "p7jRMwEOEADgAADdwKgTQOgkqv4="; // �ɹ��ջ�����

	public static final String WEIGH_TYPE_PURSENDID = "p7jRMwEOEADgAAEWwKgTQOgkqv4="; // �ɹ��˻�����

	public static final String WEIGH_TYPE_SALESENDID = "p7jRMwEOEADgAADiwKgTQOgkqv4="; // ���۷�������

	public static final String WEIGH_TYPE_SALEINCEPTID = "p7jRMwEOEADgAAEbwKgTQOgkqv4="; // �����˻�����

	public static final String WEIGH_TYPE_IRONTRANSID = "p7jRMwEOEADgAAECwKgTQOgkqv4="; // ��ˮ��������

	public static final String WEIGH_TYPE_STEELTRANSID = "p7jRMwEOEADgAAEHwKgTQOgkqv4="; // ������������

	public static final String WEIGH_TYPE_OTHERTRANSID = "p7jRMwEOEADgAAERwKgTQOgkqv4="; // ������������

	public static final String WEIGH_TYPE_MATERIALTRANSID = "p7jRMwEOEADgAAEMwKgTQOgkqv4="; // ����ԭȼ�ϵ�������

	public static final String WEIGH_TYPE_TEMPDEPOSITEDID = "LUdeGwEPEADgAAVdwKgSFegkqv4="; // ������������

	public static final String WEIGH_TYPE_CHECKUPID = "LUdeGwEPEADgAAV9wKgSFegkqv4="; // �����ü������

	public static final String WEIGH_TYPE_FLOTSAMCALLBACKID = "/Ydx6AEREADgAACNwKgSqugkqv4=";// ���ϻ��ռ������

	public static final String WEIGH_TYPE_COMPLETEINCOMEID = "/gwjBgEREADgAAHIwKgSqugkqv4=";// ��Ʒ���������

	public static final String WEIGH_TYPE_OTHERID = "Nzx/bAEaEADgAB18wKgSfOgkqv4="; // �������ID

	public static Map weighTypeNumberToId = null;

	public static Map weighTypeIdToNumber = null;

	static {

		weighTypeNumberToId = new HashMap();
		weighTypeNumberToId.put(WEIGH_TYPE_IRONTRANS_IN,
				WEIGH_TYPE_IRONTRANS_INID); // �����������
		weighTypeNumberToId.put(WEIGH_TYPE_MATERIALTRANS_IN,
				WEIGH_TYPE_MATERIALTRANS_INID); // �ڲ����������

		weighTypeNumberToId.put(WEIGH_TYPE_PURINCEPT, WEIGH_TYPE_PURINCEPTID);

		weighTypeNumberToId.put(WEIGH_TYPE_PURSEND, WEIGH_TYPE_PURSENDID);

		weighTypeNumberToId.put(WEIGH_TYPE_SALESEND, WEIGH_TYPE_SALESENDID);

		weighTypeNumberToId.put(WEIGH_TYPE_SALEINCEPT, WEIGH_TYPE_SALEINCEPTID);

		weighTypeNumberToId.put(WEIGH_TYPE_IRONTRANS, WEIGH_TYPE_IRONTRANSID);

		weighTypeNumberToId.put(WEIGH_TYPE_STEELTRANS, WEIGH_TYPE_STEELTRANSID);

		weighTypeNumberToId.put(WEIGH_TYPE_OTHERTRANS, WEIGH_TYPE_OTHERTRANSID);

		weighTypeNumberToId.put(WEIGH_TYPE_MATERIALTRANS,
				WEIGH_TYPE_MATERIALTRANSID);

		weighTypeNumberToId.put(WEIGH_TYPE_TEMPDEPOSITED,
				WEIGH_TYPE_TEMPDEPOSITEDID);

		weighTypeNumberToId.put(WEIGH_TYPE_CHECKUP, WEIGH_TYPE_CHECKUPID);

		weighTypeNumberToId.put(WEIGH_TYPE_FLOTSAMCALLBACK,
				WEIGH_TYPE_FLOTSAMCALLBACKID);

		weighTypeNumberToId.put(WEIGH_TYPE_COMPLETEINCOME,
				WEIGH_TYPE_COMPLETEINCOMEID);

		weighTypeIdToNumber = new HashMap();

		weighTypeNumberToId.put(WEIGH_TYPE_IRONTRANS_INID,
				WEIGH_TYPE_IRONTRANS_IN); // �����������
		weighTypeNumberToId.put(WEIGH_TYPE_MATERIALTRANS_INID,
				WEIGH_TYPE_MATERIALTRANS_IN); // �ڲ����������

		weighTypeIdToNumber.put(WEIGH_TYPE_PURINCEPTID, WEIGH_TYPE_PURINCEPT);

		weighTypeIdToNumber.put(WEIGH_TYPE_PURSENDID, WEIGH_TYPE_PURSEND);

		weighTypeIdToNumber.put(WEIGH_TYPE_SALESENDID, WEIGH_TYPE_SALESEND);

		weighTypeIdToNumber.put(WEIGH_TYPE_SALEINCEPTID, WEIGH_TYPE_SALEINCEPT);

		weighTypeIdToNumber.put(WEIGH_TYPE_IRONTRANSID, WEIGH_TYPE_IRONTRANS);

		weighTypeIdToNumber.put(WEIGH_TYPE_STEELTRANSID, WEIGH_TYPE_STEELTRANS);

		weighTypeIdToNumber.put(WEIGH_TYPE_OTHERTRANSID, WEIGH_TYPE_OTHERTRANS);

		weighTypeIdToNumber.put(WEIGH_TYPE_MATERIALTRANSID,
				WEIGH_TYPE_MATERIALTRANS);

		weighTypeIdToNumber.put(WEIGH_TYPE_TEMPDEPOSITEDID,
				WEIGH_TYPE_TEMPDEPOSITED);

		weighTypeIdToNumber.put(WEIGH_TYPE_CHECKUPID, WEIGH_TYPE_CHECKUP);

		weighTypeIdToNumber.put(WEIGH_TYPE_FLOTSAMCALLBACKID,
				WEIGH_TYPE_FLOTSAMCALLBACK);

		weighTypeIdToNumber.put(WEIGH_TYPE_COMPLETEINCOMEID,
				WEIGH_TYPE_COMPLETEINCOME);
	}

	/*
	 * --------------------------------------------------------------------------
	 * ----------------------------
	 */
	/* ҵ����Դ�ؼ��� */
	/*
	 * --------------------------------------------------------------------------
	 * ----------------------------
	 */
	// ����F7��ѯ
	public final static String MATERIAL_PATH = "com.kingdee.eas.basedata.master.material.app.F7MaterialBaseInfoQuery";
	// �������λ��ѯ
	public final static String MULTIUNITF7_PATH = "com.kingdee.eas.basedata.master.material.app.F7MultiMeasureUnitQuery";
	// �����ص�F7��ѯ
	public final static String CONSIGNPLACEF7_PATH = "com.kingdee.eas.st.weigh.app.F7ConsignPlaceQuery";
	// �ջ��ص�F7��ѯ
	public final static String ACCEPTPLACEF7_PATH = "com.kingdee.eas.st.weigh.app.F7AcceptPlaceQuery";
	// ���䵥λF7��ѯ
	public final static String TRAFFICORGF7_PATH = "com.kingdee.eas.st.weigh.app.F7TrafficOrgQuery";

}
