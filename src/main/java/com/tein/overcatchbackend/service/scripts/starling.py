#Import all Necessary Library
import pandas as pd
import tabula
import numpy as np
import json
import sys, getopt
import PyPDF2
from PyPDF2 import PdfFileReader



def convertStarling(pdfPath):

  pdf = PdfFileReader(open(pdfPath,'rb'))
  pagecount = pdf.getNumPages()
  print(pagecount)


  first = []
  schemeFirst = {
    "page": 1,
    "extraction_method": "guess",
    "x1": 27.903545379638675,
    "x2": 571.0925621032716,
    "y1": 225.08859939575197,
    "y2": 763.8130488586427,
    "width": 543.1890167236329,
    "height": 538.7244494628907
  }
  first.append(schemeFirst)

  with open('starlingFirst.json', 'w') as outfile:
      json.dump(first, outfile)
  outfile.close()

  

  middle = []
  for x in range(1,pagecount-1):
  
    scheme = {
    "page": x+1,
    "extraction_method": "guess",
    "x1": 29.000340736694373,
    "x2": 566.236601112671,
    "y1": 227.32088302612306,
    "y2": 764.5571434020997,
    "width": 537.2362603759766,
    "height": 537.2362603759766
  }
    middle.append(scheme)
 
   
  with open('starlingMiddle.json', 'w') as outfile:
      json.dump(middle, outfile)
  outfile.close()
 


  schemeLast = [{
    "page": pagecount,
    "extraction_method": "guess",
    "x1": 29.000340736694373,
    "x2": 569.2129792864991,
    "y1": 227.32088302612306,
    "y2": 687.1713108825684,
    "width": 540.2126385498048,
    "height": 459.8504278564453
  }] 

  with open('starlingLast.json', 'w') as outfile:
      json.dump(schemeLast, outfile)
  outfile.close()

  if(pagecount==1):
        readLast = tabula.read_pdf_with_template(""+pdfPath+"", "starlingLast.json",output_format=None,encoding="utf-8", multiple_tables=False, pandas_options={"header":None})
        df1= pd.DataFrame(np.concatenate(readLast))
        colCount = len(df1.columns)
        if(colCount==8):
          df1[2]=df1[4]
          df1[3]=df1[5]
          df1[4]=df1[6]
          df1[5]=df1[7]
          df1 = df1.drop(columns=[6,7], axis=1)
        final = pd.concat([df1], axis=0)

  elif(pagecount==2):
        readFirst = tabula.read_pdf_with_template(""+pdfPath+"", "starlingFirst.json",output_format=None,encoding="utf-8", multiple_tables=False, pages="1", pandas_options={"header":None})
        readLast = tabula.read_pdf_with_template(""+pdfPath+"", "starlingLast.json",output_format=None,encoding="utf-8", multiple_tables=False, pandas_options={"header":None})
        df1= pd.DataFrame(np.concatenate(readFirst))
        colCount = len(df1.columns)
        if(colCount==8):
          df1[2]=df1[4]
          df1[3]=df1[5]
          df1[4]=df1[6]
          df1[5]=df1[7]
          df1 = df1.drop(columns=[6,7], axis=1)
        df3= pd.DataFrame(np.concatenate(readLast))
        final = pd.concat([df1, df3], axis=0)
        
  else:
        readFirst = tabula.read_pdf_with_template(""+pdfPath+"", "starlingFirst.json",output_format=None,encoding="utf-8", multiple_tables=False, pages="1", pandas_options={"header":None})
        readMiddle = tabula.read_pdf_with_template(""+pdfPath+"", "starlingMiddle.json",output_format=None,encoding="utf-8", multiple_tables=False, pandas_options={"header":None})
        readLast = tabula.read_pdf_with_template(""+pdfPath+"", "starlingLast.json",output_format=None,encoding="utf-8", multiple_tables=False, pandas_options={"header":None})
        df1= pd.DataFrame(np.concatenate(readFirst))
        colCount = len(df1.columns)
        if(colCount==8):
          df1[2]=df1[4]
          df1[3]=df1[5]
          df1[4]=df1[6]
          df1[5]=df1[7]
          df1 = df1.drop(columns=[6,7], axis=1)
        df2= pd.DataFrame(np.concatenate(readMiddle))
        df3= pd.DataFrame(np.concatenate(readLast))
        final = pd.concat([df1, df2, df3], axis=0)

  
  final.to_excel(""+pdfPath+".xlsx",header=False)



param1 = sys.argv[1]
convertStarling(param1)
finalPath = ""+param1+".xlsx"
print(finalPath)
