package com.example.quiz_management;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;

public class startQuizActivity extends AppCompatActivity {

    ImageView act_Bg;
    Button start_quiz;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        /*
        gateway for starting the quiz
         */

        requestWindowFeature(Window.FEATURE_NO_TITLE); //will hide the title
        this.getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start_quiz);

        act_Bg = findViewById(R.id.imageView);
        start_quiz = findViewById(R.id.button);

        start_quiz.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent_startQuiz_quiz = new Intent(startQuizActivity.this, QuizActivity.class);
                startActivity(intent_startQuiz_quiz);
            }
        });
    }


}






























































































/*
Cannot load settings from file 'F:\chetan\working stuff\Project\app dev\Quiz mangement\Code\.idea\modules\Quiz_management.iml': ParseError at [row,col]:[4,7] Message: Content is not allowed in trailing section. Please correct the file content
C:/Program Files/Android/Android Studio/plugins/java/lib/jdkAnnotations.jar!/java/awt/event/annotations.xml

org.xml.sax.SAXParseException; lineNumber: 2; columnNumber: 2; The markup in the document preceding the root element must be well-formed.
	at org.apache.xerces.util.ErrorHandlerWrapper.createSAXParseException(Unknown Source)
	at org.apache.xerces.util.ErrorHandlerWrapper.fatalError(Unknown Source)
	at org.apache.xerces.impl.XMLErrorReporter.reportError(Unknown Source)
	at org.apache.xerces.impl.XMLErrorReporter.reportError(Unknown Source)
	at org.apache.xerces.impl.XMLErrorReporter.reportError(Unknown Source)
	at org.apache.xerces.impl.XMLScanner.reportFatalError(Unknown Source)
	at org.apache.xerces.impl.XMLDocumentScannerImpl$PrologDispatcher.dispatch(Unknown Source)
	at org.apache.xerces.impl.XMLDocumentFragmentScannerImpl.scanDocument(Unknown Source)
	at org.apache.xerces.parsers.XML11Configuration.parse(Unknown Source)
	at org.apache.xerces.parsers.XML11Configuration.parse(Unknown Source)
	at org.apache.xerces.parsers.XMLParser.parse(Unknown Source)
	at org.apache.xerces.parsers.AbstractSAXParser.parse(Unknown Source)
	at org.apache.xerces.jaxp.SAXParserImpl$JAXPSAXParser.parse(Unknown Source)
	at org.apache.xerces.jaxp.SAXParserImpl.parse(Unknown Source)
	at com.intellij.codeInsight.BaseExternalAnnotationsManager.loadData(BaseExternalAnnotationsManager.java:213)
	at com.intellij.openapi.projectRoots.impl.JavaSdkImpl.isInternalJdkAnnotationRootCorrect(JavaSdkImpl.java:324)
	at com.intellij.openapi.projectRoots.impl.JavaSdkImpl.attachIDEAAnnotationsToJdk(JavaSdkImpl.java:294)
	at com.intellij.codeInspection.magicConstant.MagicConstantInspection.lambda$attachAnnotationsLaterTo$1(MagicConstantInspection.java:185)
	at com.intellij.openapi.application.TransactionGuardImpl$2.run(TransactionGuardImpl.java:201)
	at com.intellij.openapi.application.impl.ApplicationImpl.runIntendedWriteActionOnCurrentThread(ApplicationImpl.java:808)
	at com.intellij.openapi.application.impl.ApplicationImpl.lambda$invokeLater$4(ApplicationImpl.java:328)
	at com.intellij.openapi.application.impl.FlushQueue.doRun(FlushQueue.java:84)
	at com.intellij.openapi.application.impl.FlushQueue.runNextEvent(FlushQueue.java:132)
	at com.intellij.openapi.application.impl.FlushQueue.flushNow(FlushQueue.java:47)
	at com.intellij.openapi.application.impl.FlushQueue$FlushNow.run(FlushQueue.java:188)
	at java.desktop/java.awt.event.InvocationEvent.dispatch(InvocationEvent.java:313)
	at java.desktop/java.awt.EventQueue.dispatchEventImpl(EventQueue.java:776)
	at java.desktop/java.awt.EventQueue$4.run(EventQueue.java:727)
	at java.desktop/java.awt.EventQueue$4.run(EventQueue.java:721)
	at java.base/java.security.AccessController.doPrivileged(Native Method)
	at java.base/java.security.ProtectionDomain$JavaSecurityAccessImpl.doIntersectionPrivilege(ProtectionDomain.java:85)
	at java.desktop/java.awt.EventQueue.dispatchEvent(EventQueue.java:746)
	at com.intellij.ide.IdeEventQueue.defaultDispatchEvent(IdeEventQueue.java:971)
	at com.intellij.ide.IdeEventQueue._dispatchEvent(IdeEventQueue.java:841)
	at com.intellij.ide.IdeEventQueue.lambda$null$8(IdeEventQueue.java:452)
	at com.intellij.openapi.progress.impl.CoreProgressManager.computePrioritized(CoreProgressManager.java:744)
	at com.intellij.ide.IdeEventQueue.lambda$dispatchEvent$9(IdeEventQueue.java:451)
	at com.intellij.openapi.application.impl.ApplicationImpl.runIntendedWriteActionOnCurrentThread(ApplicationImpl.java:808)
	at com.intellij.ide.IdeEventQueue.dispatchEvent(IdeEventQueue.java:499)
	at java.desktop/java.awt.EventDispatchThread.pumpOneEventForFilters(EventDispatchThread.java:203)
	at java.desktop/java.awt.EventDispatchThread.pumpEventsForFilter(EventDispatchThread.java:124)
	at java.desktop/java.awt.EventDispatchThread.pumpEventsForHierarchy(EventDispatchThread.java:113)
	at java.desktop/java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:109)
	at java.desktop/java.awt.EventDispatchThread.pumpEvents(EventDispatchThread.java:101)
	at
 */