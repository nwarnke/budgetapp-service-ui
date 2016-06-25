package com.controller.advice;

class ClientError
{
	private String	title;
	private String	message;

	public ClientError(final String title, final String message)
	{
		this.title = title;
		this.message = message;
	}

	@Override
	public String toString()
	{
		final StringBuilder builder = new StringBuilder();
		builder.append("ClientError [title=").append(title).append(", message=").append(message).append("]");
		return builder.toString();
	}

	public String getTitle()
	{
		return title;
	}

	public String getMessage()
	{
		return message;
	}

	public void setTitle(final String title)
	{
		this.title = title;
	}

	public void setMessage(final String message)
	{
		this.message = message;
	}

}
