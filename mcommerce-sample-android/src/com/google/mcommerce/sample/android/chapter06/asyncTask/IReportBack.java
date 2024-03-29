package com.google.mcommerce.sample.android.chapter06.asyncTask;

/*
 * An interface implemented typically by an activity
 * so that a worker class can report back
 * on what happened.
 */
public interface IReportBack 
{
	public void reportBack(String tag, String message);
	public void reportTransient(String tag, String message);
}
