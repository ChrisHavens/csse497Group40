USE [Humanitarian]
GO

/****** Object:  Table [dbo].[Phone]    Script Date: 10/18/2015 6:02:19 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Phone](
	[ID] [int] NOT NULL,
	[Phone#] [nchar](20) NOT NULL,
	[PhoneDirty] [bit] NOT NULL
) ON [PRIMARY]

GO

