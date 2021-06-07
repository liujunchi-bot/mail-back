package utils;

import java.util.ArrayList;
import java.util.List;

/*
Qiao Qing
2020/04/05
*/

/*
功能：传入一个List，根据页号返回所需要的数据

用法：
ArrayList<User> = new UserMgr().getUser();  //调用Dao得到User信息
Pager<User> pager = new Pager<>();  //new一个分页器
pager.setBigList(list); //将大集合传入分页器

pager.setCountPerPage(10); //需要更改每一页多少条数据时调用
pager.setCurrentPageIndex(2);   //需要切换页码时调用

pager.getRecordCount(); //得到数据总条数
pager.getPageCount();   //得到总页数
pager.getSmallList();   //得到小集合
 */

public class Pager<T> {
    private List<T> bigList; // 大集合，即外界传入的List
    private int currentPageIndex = 1; // 当前页号，外界传入
    private int countPerPage = 20; // 每页条数，外界也可以设定
    private List<T> smallList; // 小的集合，即需要返回的结果
    private int pageCount; // 页数
    private int recordCount; // 记录条数
    private int prePageIndex; // 上一页序号
    private int nextPageIndex; // 下一页序号
    private boolean isFirstPage; // 是否第一页
    private boolean isLastPage; // 是否最后一页

    //当List没有改变、当前页码改变时，调用该函数
    public void setCurrentPageIndex(int currentPageIndex) {
        this.currentPageIndex = currentPageIndex;

        //确定上一页与下一页
        prePageIndex = currentPageIndex - 1;
        nextPageIndex = currentPageIndex + 1;

        //是否为第一页
        if(currentPageIndex==1) {
            isFirstPage = true;
        } else {
            isFirstPage = false;
        }

        //是否为最后一页
        if (currentPageIndex == pageCount) {
            isLastPage = true;
        } else {
            isLastPage = false;
        }

        //得到小集合
        smallList = new ArrayList<T>();
        for (int i = (currentPageIndex - 1) * countPerPage;
             i < currentPageIndex * countPerPage && i < recordCount; i++) {
            smallList.add(bigList.get(i));
        }
    }

    //返回当前页码
    public int getCurrentPageIndex() {
        return currentPageIndex;
    }

    //返回大集合
    public List<T> getBigList() {
        return bigList;
    }

    //设置大集合
    public void setBigList(List<T> bigList) {
        this.bigList = bigList;
        //计算数据总数
        recordCount = bigList.size();
        //计算总页数
        if (recordCount % countPerPage == 0) {
            pageCount = recordCount / countPerPage;
        } else {
            pageCount = recordCount / countPerPage + 1;
        }
        //计算小集合，currentPageIndex取默认值1
        smallList = new ArrayList<T>();
        for (int i = (currentPageIndex - 1) * countPerPage;
             i < currentPageIndex * countPerPage && i < recordCount; i++) {
            smallList.add(bigList.get(i));
        }
    }

    //得到每页条数
    public int getCountPerPage() {
        return countPerPage;
    }

    //设定每页条数
    public void setCountPerPage(int countPerPage) {
        this.countPerPage = countPerPage;
        setBigList(bigList);
        setCurrentPageIndex(1);
    }

    //得到小集合
    public List<T> getSmallList() {
        return smallList;
    }

    //得到总页数
    public int getPageCount() {
        return pageCount;
    }

    //得到总的记录数目
    public int getRecordCount() {
        return recordCount;
    }

    //得到下一页的页码
    public int getNextPageIndex() {
        return nextPageIndex;
    }

    //得到上一页的页码
    public int getPrePageIndex() {
        return prePageIndex;
    }

    //当前页是否为第一页
    public boolean isFirstPage() {
        return isFirstPage;
    }

    //当前页是否为最后一页
    public boolean isLastPage() {
        return isLastPage;
    }
}