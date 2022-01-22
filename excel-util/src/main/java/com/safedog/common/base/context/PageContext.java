package com.safedog.common.base.context;


import com.safedog.common.base.model.PageVO;

/**
 * 用于存放当前访问线程的上下文信息
 * @author chenxiaojian
 */
public class PageContext {


	private static ThreadLocal<PageVO> page = new ThreadLocal<>();
	/**
	 * 判断是否需要清除
	 */
	private static ThreadLocal<Boolean> pageCleanFlag = new ThreadLocal<>();

	public static PageVO getPage() {
		PageVO pageVO = page.get();
		if (pageVO == null) {
			pageVO = new PageVO(PageVO.DEFAULT_PAGE_SIZE, PageVO.DEFAULT_PAGE_NO);
		}
		return pageVO;
	}

	public static void setPage(PageVO pageVO) {
		page.set(pageVO);
	}

	public static void removePage() {
		if (page != null) {
			page.remove();
		}
	}

	public static Boolean getPageCleanFlag() {
		return pageCleanFlag.get();
	}

	public static void setPageCleanFlag(boolean flag) {
		pageCleanFlag.set(flag);
	}
}
