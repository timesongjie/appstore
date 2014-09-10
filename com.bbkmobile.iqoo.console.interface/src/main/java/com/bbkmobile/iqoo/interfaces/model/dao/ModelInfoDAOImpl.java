package com.bbkmobile.iqoo.interfaces.model.dao;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.bbkmobile.iqoo.console.dao.modelmgr.Model;
import com.bbkmobile.iqoo.console.dao.modelmgr.Series;
import com.bbkmobile.iqoo.interfaces.common.AnnotationBaseDao;

@Repository("iModelInfoDAO")
public class ModelInfoDAOImpl extends AnnotationBaseDao implements ModelInfoDAO {

    // @SuppressWarnings("unchecked")
    // public List<Model> getModelInfo() throws Exception {
    // return getHibernateTemplate().find("from Model order by show_order ASC");
    // }
    //
    // //添加机型
    // public boolean addModel(Model model) throws Exception {
    // boolean result=false;
    // if(null != model)
    // {
    // model.setAdd_date(new Date());
    // ConsoleConstant constant=(ConsoleConstant)
    // getHibernateTemplate().find("from ConsoleConstant where type=2 and value="+model.getSdkVersion()).get(0);
    // model.setMd_sys(constant.getDescribe());
    // getHibernateTemplate().save(model);
    // result=true;
    // }
    // return result;
    // }
    //
    // //删除某目录下文件名字包含指定字符的文件
    // public void deleteDirFilesLikeName(String dir, String likeName){
    // File file = new File(dir);
    // if(file.exists()){
    // if(file.isDirectory())
    // {
    // File[] files = file.listFiles();
    // for(int i = 0; i < files.length; i++){
    // deleteFilesLikeName(files[i], likeName);
    // }
    // }
    // // deleteFilesLikeName(file, likeName);
    // } else {
    // }
    // }
    //
    // //删除文件名包含指定字符的文件
    // public void deleteFilesLikeName(File file, String likeName)
    // {
    // if(file.isFile()){
    // //是文件
    // String temp="";
    // if(file.getName().contains("."))
    // {
    // temp= file.getName().substring(0,file.getName().lastIndexOf("."));
    // }
    // else
    // {
    // temp=file.getName();
    // }
    // if(temp.indexOf(likeName) != -1){
    // file.delete();
    // }
    // } else {
    // //是目录
    // // File[] files = file.listFiles();
    // // for(int i = 0; i < files.length; i++){
    // // deleteFilesLikeName(files[i], likeName);
    // // }
    // }
    //
    // }
    //
    // public boolean saveModelImage(File file, String file_name, String dir)
    // throws Exception {
    // try{
    // //fileName = UtilTool.changeFileName(fileName);
    // deleteDirFilesLikeName(dir,file_name.substring(0,file_name.lastIndexOf("_")));
    // java.io.InputStream input = new java.io.FileInputStream(file);
    // java.io.OutputStream output = new
    // java.io.FileOutputStream(dir+file_name);
    // byte buffer[] = new byte[8192];
    // int count = 0;
    // while((count = input.read(buffer)) > 0)
    // {
    // output.write(buffer, 0, count);
    // }
    // input.close();
    // output.close();
    // }catch (Exception e) {
    // // TODO: handle exception
    // throw e;
    // }
    // return true;
    // }
    //
    // public Model findModelById(Short id) throws Exception {
    // // TODO Auto-generated method stub
    // try{
    // return
    // (Model)getHibernateTemplate().find("from Model where id=?",id).get(0);
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    // //修改机型
    // public boolean updateModel(Model model) throws Exception{
    // try{
    // ConsoleConstant constant=(ConsoleConstant)
    // getHibernateTemplate().find("from ConsoleConstant where type=2 and value="+model.getSdkVersion()).get(0);
    // model.setMd_sys(constant.getDescribe());
    // model.setModify_date(new Timestamp(System.currentTimeMillis()));
    // // String queryString
    // ="update Model set md_name=?,md_sys=?,drawable_dpi=?,series_id=?,platform=?,CPU_ABI=?,sdkVersion=?,modify_date=? where id=?";
    // // String queryString2
    // ="update Model set md_name=?,md_sys=?,drawable_dpi=?,series_id=?,platform=?,CPU_ABI=?,sdkVersion=?,modify_date=? where id=?";
    //
    // // Session session=getSession();
    // // Query queryObject=session.createQuery(queryString);
    // // queryObject.setString(0, model.getMd_name());
    // // queryObject.setString(1, model.getMd_sys());
    // // // queryObject.setInteger(2, model.getShow_order());
    // // queryObject.setString(2, model.getDrawable_dpi());
    // // queryObject.setLong(3,model.getSeries_id());
    // // queryObject.setString(4, model.getPlatform());
    // // queryObject.setString(5, model.getCPU_ABI());
    // // queryObject.setInteger(6, model.getSdkVersion());
    // // queryObject.setTimestamp(7, new Date());
    // // queryObject.setLong(8, model.getId());
    // // queryObject.executeUpdate();
    // Model oldModel=this.getHibernateTemplate().load(Model.class,
    // model.getId());
    // oldModel.setCPU_ABI(model.getCPU_ABI());
    // oldModel.setDrawable_dpi(model.getDrawable_dpi());
    // oldModel.setMd_icon(model.getMd_icon());
    // oldModel.setMd_name(model.getMd_name());
    // oldModel.setMd_sys(model.getMd_sys());
    // oldModel.setModify_date(model.getModify_date());
    // oldModel.setPlatform(model.getPlatform());
    // oldModel.setSdkVersion(model.getSdkVersion());
    // oldModel.setSeries_id(model.getSeries_id());
    // this.getHibernateTemplate().update(oldModel);
    // return true;
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    // public boolean deleteModelImage(String path) throws Exception{
    // try{
    // File file = new File(path);
    // file.delete();
    // }catch (Exception e) {
    // // TODO: handle exception
    // throw e;
    // }
    // return true;
    // }
    //
    // public boolean deleteModelByID(Short[] ids) throws Exception {
    // // TODO Auto-generated method stub
    // try{
    // String queryString ="delete Model where id=?";
    // String queryString2="from AppInfo where filter_model like '%";
    // String temp="";
    // List<AppInfo> appInfos=null;
    // Session session=getSession();
    // Query queryObj=null;
    // for(Short id:ids){
    // queryObj=session.createQuery(queryString);
    // queryObj.setLong(0, id);
    // queryObj.executeUpdate();
    //
    // //删除机型时，去掉t_app_info表中机型过滤字段filter_model中该机型的ID
    // temp=","+id+",";
    // queryObj=session.createQuery(queryString2+temp+"%'");
    // appInfos=queryObj.list();
    // if(null!=appInfos && appInfos.size()>0)
    // {
    // for(AppInfo app : appInfos)
    // {
    // temp=app.getFilter_model().replace(","+id+",", ",");
    // if(",".equals(temp))
    // {
    // temp="";
    // app.setAppStatus((short)0);
    // }
    // app.setFilter_model(temp);
    // this.getHibernateTemplate().update(app);
    // }
    // }
    //
    // }
    // return true;
    // }catch(Exception e){
    // throw e;
    // }
    //
    // }
    //
    // public boolean updateShowOrder(Short id, int order) throws Exception {
    // // TODO Auto-generated method stub
    // try{
    // String queryString ="update Model set show_order=? where id=?";
    // Session session=getSession();
    // Query queryObj=session.createQuery(queryString);
    // queryObj.setInteger(0, order);
    // queryObj.setLong(1, id);
    // queryObj.executeUpdate();
    // return true;
    // }catch(Exception e){
    // throw e;
    // }
    // }
    @Override
    public Series getSeriesWithName(String seriesName) throws Exception {
        try {
            Series series = null;
            List<Series> seriesList = getHibernateTemplate().find(
                    "from Series where series=?", seriesName);
            if (null != seriesList && seriesList.size() > 0) {
                series = (Series) seriesList.get(0);
            }
            return series;
        } catch (Exception e) {
            throw e;
        }
    }

    // @SuppressWarnings("unchecked")
    // public List<Series> getSeriesList() throws Exception{
    // try{
    // return getHibernateTemplate().find("from Series");
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    public Model findModelByMdName(String md_name) throws Exception {
        try {
            Model model = null;
            List<Model> models = getHibernateTemplate().find(
                    "from Model where md_name =?", md_name);
            if (null != models && models.size() > 0) {
                return model = (Model) models.get(0);
            }
            return model;
            // return
            // (Model)getHibernateTemplate().find("from Model where md_name =?",md_name).get(0);
        } catch (Exception e) {
            throw e;
        }
    }
    //
    // @Override
    // public List<ConsoleConstant> getSdkList() throws Exception {
    // try{
    // return getHibernateTemplate().find("from ConsoleConstant where type=2");
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    // @Override
    // public List<ConsoleConstant> getDrawableList() throws Exception {
    // try{
    // return
    // getHibernateTemplate().find("from ConsoleConstant where type=1 and value like ?","phone%");
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    // @Override
    // public List<ConsoleConstant> getPcDrawableList() throws Exception {
    // try{
    // return
    // getHibernateTemplate().find("from ConsoleConstant where type=1 and value=?","pc");
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    // @Override
    // public List<ConsoleConstant> getPlatformList() throws Exception {
    // try{
    // return getHibernateTemplate().find("from ConsoleConstant where type=3");
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    // @Override
    // public List<ConsoleConstant> getAllDrawableList() throws Exception {
    // // TODO Auto-generated method stub
    // try{
    // return
    // getHibernateTemplate().find("from ConsoleConstant where type=1 and value!=? and value!=?","phone","pc");
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    // @Override
    // public void updateModelIcon(Model model) throws Exception {
    // // TODO Auto-generated method stub
    // try{
    // String queryString ="update Model set md_icon=? where id=?";
    // Session session=getSession();
    // Query queryObj=session.createQuery(queryString);
    // queryObj.setString(0, model.getMd_icon());
    // queryObj.setShort(1, model.getId());
    // queryObj.executeUpdate();
    // }catch(Exception e){
    // throw e;
    // }
    // }
    //
    // @Override
    // public String findModelByScreenIds(String screenModel, Short[] screenIds)
    // throws Exception {
    // // TODO Auto-generated method stub
    // // String screenModel=""; //所有分辨率对应的机型
    // if(!"".equals(screenModel))
    // {
    // screenModel=","+screenModel+",";
    // }
    // else
    // {
    // screenModel=",";
    // }
    // if(null!=screenIds && screenIds.length>0)
    // {
    // ConsoleConstant screen=null;
    // List<Model> models=null;
    // String temp="";
    // for(Short id : screenIds)
    // {
    // screen=this.getHibernateTemplate().load(ConsoleConstant.class, id);
    // if(null!=screen)
    // {
    // models=
    // getHibernateTemplate().find("from Model where drawable_dpi=?",screen.getValue());
    // if(null!=models && models.size()>0)
    // {
    // for(Model m : models)
    // {
    // temp=","+m.getId()+",";
    // if(!screenModel.contains(temp))
    // {
    // screenModel+=m.getId()+",";
    // }
    // }
    // }
    // }
    // }
    // }
    // if(!"".equals(screenModel) && !",".equals(screenModel))
    // {
    // screenModel=screenModel.substring(1,screenModel.lastIndexOf(","));
    // }
    // else
    // {
    // screenModel="";
    // }
    // return screenModel;
    // }
    //
    // @Override
    // public int findScreenId(String drawableDpi) throws Exception {
    // // TODO Auto-generated method stub
    //
    // List<ConsoleConstant>
    // screen=getHibernateTemplate().find("from ConsoleConstant where value=?",drawableDpi);
    // if(null!=screen && screen.size()==1)
    // {
    // return screen.get(0).getId();
    // }
    // return 0;
    // }

}
