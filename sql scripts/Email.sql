USE [Humanitarian]
GO

/****** Object:  Table [dbo].[Email]    Script Date: 10/18/2015 6:00:57 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Email](
	[ID] [int] NOT NULL,
	[Email] [nchar](30) NOT NULL,
	[EmailDirty] [bit] NOT NULL
) ON [PRIMARY]

GO

