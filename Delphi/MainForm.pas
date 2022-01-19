unit MainForm;

interface

uses
  Winapi.Windows,
  Winapi.Messages,
  System.SysUtils,
  System.Variants,
  System.Classes,
  Vcl.Graphics,
  Vcl.Controls,
  Vcl.Forms,
  Vcl.Dialogs,
  Vcl.StdCtrls,
  StompClient;

type
  TForm4 = class(TForm, IStompClientListener)
    Button1: TButton;
    Memo1: TMemo;
    Button2: TButton;
    Button3: TButton;
    procedure Button1Click(Sender: TObject);
    procedure FormDestroy(Sender: TObject);
    procedure FormCreate(Sender: TObject);
    procedure Button2Click(Sender: TObject);
    procedure Button3Click(Sender: TObject);
  private
    FSTOMPListener: IStompListener;
    FSTOMPClient: IStompClient;
    FFormClosing: Boolean;
    FProducerThread: TThread;
  public
    procedure OnMessage(StompFrame: IStompFrame; var TerminateListener: Boolean);
    procedure OnListenerStopped(StompClient: IStompClient);
  end;

var
  Form4: TForm4;

implementation

{$R *.dfm}


procedure TForm4.Button1Click(Sender: TObject);
begin
  FSTOMPListener.StopListening;
  Memo1.Lines.Add('Listener Started');
  FSTOMPListener.StartListening;
end;

procedure TForm4.Button2Click(Sender: TObject);
begin
  FSTOMPListener.StopListening;
end;

procedure TForm4.Button3Click(Sender: TObject);
var
  i: Integer;
  stomp: IStompClient;
begin
  //alterado pra enviar apenas 10 msg em loop
  stomp := StompUtils.StompClientAndConnect;
  for I := 1 to 10 do
  begin
    stomp.Send('/queue/fila.financeiro', 'Hello World ' + IntToStr(i));
  end;
  stomp.Disconnect;
end;

procedure TForm4.FormCreate(Sender: TObject);
begin
  FFormClosing := False;
  FSTOMPClient := StompUtils.StompClientAndConnect;
  FSTOMPClient.Subscribe('/queue/fila.financeiro',
    amClient,
    StompUtils.Headers.Add('include-seq', 'seq'));
  FSTOMPListener := StompUtils.CreateListener(FSTOMPClient, Self);
end;

procedure TForm4.FormDestroy(Sender: TObject);
begin
  FFormClosing := True;
  if Assigned(FProducerThread) then
  begin
    FProducerThread.WaitFor;
    FProducerThread.Free;
  end;
  FSTOMPListener := nil;
end;

procedure TForm4.OnMessage(StompFrame: IStompFrame; var TerminateListener: Boolean);
begin
  Memo1.Lines.Add(StompFrame.Body);
  TerminateListener := FFormClosing;
  FSTOMPClient.Ack(StompFrame.MessageID);
end;

procedure TForm4.OnListenerStopped(StompClient: IStompClient);
begin
  Memo1.Lines.Add('Listener Stopped');
end;

end.
