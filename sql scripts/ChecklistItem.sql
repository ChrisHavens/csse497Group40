USE [Humanitarian]
GO

/****** Object:  Table [dbo].[ChecklistItem]    Script Date: 10/18/2015 6:00:48 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[ChecklistItem](
	[ID] [nchar](10) NOT NULL,
	[List] [int] NOT NULL,
	[Person?] [int] NULL,
	[PersonDirty] [bit] NOT NULL,
	[Done] [bit] NOT NULL,
	[DoneDirty] [bit] NOT NULL,
	[Info] [nchar](140) NOT NULL,
	[InfoDirty] [bit] NOT NULL,
 CONSTRAINT [PK_ChecklistItem] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

