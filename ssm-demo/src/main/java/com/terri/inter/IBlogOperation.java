package com.terri.inter;

import java.util.List;
import java.util.Map;

import com.terri.model.Blog;

public interface IBlogOperation {

	public List<Blog> dynamicIfTest(Blog t);

	public Blog dynamicChooseTest(Blog t);

	public Blog dynamicTrimTest(Blog t);

	public Blog dynamicWhereTest(Blog t);

	public Blog dynamicSetTest(Blog t);

	public List<Blog> dynamicForeachTest(List<Integer> ids);

	public List<Blog> dynamicForeach2Test(int[] ids);

	public List<Blog> dynamicForeach3Test(Map<String, Object> params);

}
