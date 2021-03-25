/**
 * 
 */
package com.npst.mobileservice.service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.npst.mobileservice.dao.MasterConfigDao;
import com.npst.mobileservice.object.MasterConfigVO;
import com.npst.upi.hor.MasterConfig;

/**
 * @author npst
 *
 */
public class MasterConfigService {

	private static final int ACTIVE = 1;
	private static MasterConfigDao masterRepo = new MasterConfigDao();
	private static Map<String, List<MasterConfigVO>> result;

	public Map<String, List<MasterConfigVO>> findAll() {
		if (result != null)
			return result;
		if (null == masterRepo)
			masterRepo = new MasterConfigDao();
		List<MasterConfig> entities = masterRepo.findByStatus(ACTIVE) != null ? masterRepo.findByStatus(ACTIVE)
				: Collections.emptyList();
		List<MasterConfigVO> createList = null;
		MasterConfigVO configVO = null;
		result = new HashMap<>();
		for (MasterConfig config : entities) {
			if (null == result.get(config.getCodeType())) {
				createList = new ArrayList<>();
				configVO = createmasterVO(config);
				createList.add(configVO);
				result.put(config.getCodeType(), createList);
			} else {
				List<MasterConfigVO> currentType = result.get(config.getCodeType());
				configVO = createmasterVO(config);
				currentType.add(configVO);
				result.put(config.getCodeType(), currentType);
			}
		}
		return result;
	}

	private MasterConfigVO createmasterVO(MasterConfig config) {
		MasterConfigVO configVO;
		configVO = new MasterConfigVO();
		configVO.setCode(config.getCode());
		configVO.setValue(config.getValue());
		return configVO;
	}

	public List<MasterConfigVO> findByCodeType(final String codeType) {
		if (null == masterRepo)
			masterRepo = new MasterConfigDao();
		Map<String, List<MasterConfigVO>> entitiesMap = findAll();
		return entitiesMap.get(codeType);
	}

	public void clearCache() {
		result.clear();
		return;
	}
}
