package com.minlessika.sdk.report.birt;

import com.minlessika.sdk.report.Report;
import com.minlessika.sdk.report.ReportFormat;
import org.eclipse.birt.core.framework.Platform;
import org.eclipse.birt.report.engine.api.EXCELRenderOption;
import org.eclipse.birt.report.engine.api.EngineConfig;
import org.eclipse.birt.report.engine.api.EngineConstants;
import org.eclipse.birt.report.engine.api.IReportEngine;
import org.eclipse.birt.report.engine.api.IReportEngineFactory;
import org.eclipse.birt.report.engine.api.IReportRunnable;
import org.eclipse.birt.report.engine.api.IRunAndRenderTask;
import org.eclipse.birt.report.engine.api.PDFRenderOption;
import org.eclipse.birt.report.engine.api.RenderOption;
import org.eclipse.core.internal.registry.RegistryProviderFactory;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;

public final class BirtReport implements Report {

	private final String name;
	private final Map<String, Object> parameters;
	private final Map<String, Object> context;
	
	public BirtReport(final String name) {
		this(name, new HashMap<>(), new HashMap<>());
	}
	
	public BirtReport(final String name, final Map<String, Object> context) {
		this(name, new HashMap<>(), context);
	}
	
	public BirtReport(final String name, final Map<String, Object> parameters, final Map<String, Object> context) {
		this.name = name;
		this.parameters = parameters;
		this.context = context;
	}
	
	@Override
	public String name() {
		return name;
	}

	@SuppressWarnings("unchecked")
	public void render(ReportFormat format, OutputStream output) throws IOException {
			
		IReportEngine engine = null;						
		
		try {							
			final EngineConfig config = new EngineConfig();
			
			Platform.startup(config);
			final IReportEngineFactory factory = (IReportEngineFactory) Platform.createFactoryObject(IReportEngineFactory.EXTENSION_REPORT_ENGINE_FACTORY);
			engine = factory.createReportEngine(config);
						
			String filePath = String.format("reports/%s.rptdesign", name);
			final InputStream reportResource = getClass().getClassLoader().getResourceAsStream(filePath);
			IReportRunnable runnable = null;
			
			runnable = engine.openReportDesign(reportResource);
			final IRunAndRenderTask task = engine.createRunAndRenderTask(runnable);
			
			if(context.containsKey(Locale.class.getSimpleName())) {
				task.setLocale((Locale)context.get(Locale.class.getSimpleName()));
			}			

			final RenderOption pdfOptions;
			
			switch (format) {
				case XLS:
					pdfOptions = new EXCELRenderOption();
					pdfOptions.setOutputFormat("XLS");
					break;
				case POSTSCRIPT:
					pdfOptions = new RenderOption();
					pdfOptions.setOutputFormat("postscript");
					break;
				default:
					pdfOptions = new PDFRenderOption();
					pdfOptions.setOutputFormat("PDF");
			}			
																
			pdfOptions.setOutputStream(output);
			task.setRenderOption(pdfOptions);
			
			task.getAppContext().put(EngineConstants.APPCONTEXT_CLASSLOADER_KEY, getClass().getClassLoader());
			for (Entry<String, Object> item : context.entrySet()) {
				task.getAppContext().put(item.getKey(), item.getValue());
			}

			task.setParameterValues(parameters);
			task.validateParameters();
			
			task.run();
			task.close();													
		} catch(Exception ex){
			ex.printStackTrace();
			throw new IOException("An error occured during report generation !");
		}finally {
			try
			{
				engine.destroy();
				Platform.shutdown();
				//Bugzilla 351052
				RegistryProviderFactory.releaseDefault();
			}catch ( Exception e1 ){
			    e1.printStackTrace();
			}
		}
	}
	
}
