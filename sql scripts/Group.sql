USE [Humanitarian]
GO

/****** Object:  Table [dbo].[Group]    Script Date: 10/18/2015 6:01:06 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Group](
	[ID] [int] NOT NULL,
	[Name] [nchar](50) NOT NULL,
	[NameDirty] [bit] NOT NULL,
	[SuperID] [int] NOT NULL,
	[SuperIDDirty] [bit] NOT NULL,
 CONSTRAINT [PK_Group] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

