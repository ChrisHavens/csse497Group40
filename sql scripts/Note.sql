USE [Humanitarian]
GO

/****** Object:  Table [dbo].[Note]    Script Date: 10/18/2015 6:01:32 PM ******/
SET ANSI_NULLS ON
GO

SET QUOTED_IDENTIFIER ON
GO

CREATE TABLE [dbo].[Note](
	[ID] [int] NOT NULL,
	[Name] [nchar](50) NOT NULL,
	[NameDirty] [bit] NOT NULL,
	[Contents] [nchar](140) NOT NULL,
	[ContentsDirty] [bit] NOT NULL,
	[OwnerID] [int] NOT NULL,
	[OwnerIDDirty] [bit] NOT NULL,
 CONSTRAINT [PK_Notes] PRIMARY KEY CLUSTERED 
(
	[ID] ASC
)WITH (PAD_INDEX = OFF, STATISTICS_NORECOMPUTE = OFF, IGNORE_DUP_KEY = OFF, ALLOW_ROW_LOCKS = ON, ALLOW_PAGE_LOCKS = ON) ON [PRIMARY]
) ON [PRIMARY]

GO

