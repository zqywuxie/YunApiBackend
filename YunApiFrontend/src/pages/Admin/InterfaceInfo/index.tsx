import { InterfaceRequestMethodEnum } from '@/enum/commonEnum';
import CreateModel from '@/pages/Admin/InterfaceInfo/components/CreateModel';
import UpdateModel from '@/pages/Admin/InterfaceInfo/components/UpdateModel';
import {
  addInterfaceUsingPOST,
  deleteInterfaceUsingPOST,
  listInterfaceInfoByPageUsingGET,
  offlineInterfaceUsingPOST,
  onlineInterfaceUsingPOST,
  updateInterfaceUsingPOST,
} from '@/services/YunApi/interfaceController';
import { PlusOutlined } from '@ant-design/icons';
import type { ActionType, ProColumns, ProDescriptionsItemProps } from '@ant-design/pro-components';
import {
  FooterToolbar,
  ModalForm,
  PageContainer,
  ProDescriptions,
  ProFormText,
  ProFormTextArea,
  ProTable,
} from '@ant-design/pro-components';
import '@umijs/max';
import { Button, Drawer, message, Tag } from 'antd';
import { SortOrder } from 'antd/lib/table/interface';
import React, { useRef, useState } from 'react';
import { Link } from 'umi';

const TableList: React.FC = () => {
  /**
   * @en-US Pop-up window of new window
   * @zh-CN 新建窗口的弹窗
   *  */
  const [createModalOpen, handleModalOpen] = useState<boolean>(false);
  /**
   * @en-US The pop-up window of the distribution update window
   * @zh-CN 分布更新窗口的弹窗
   * */
  const [updateModalOpen, handleUpdateModalOpen] = useState<boolean>(false);
  const [showDetail, setShowDetail] = useState<boolean>(false);
  const actionRef = useRef<ActionType>();
  const [currentRow, setCurrentRow] = useState<API.InterfaceInfo>();
  const [selectedRowsState, setSelectedRows] = useState<API.InterfaceInfo[]>([]);

  /**
   * @en-US Add node
   * @zh-CN 添加接口信息
   * @param fields
   */
  const handleAdd = async (fields: API.InterfaceInfo) => {
    const hide = message.loading('正在添加');
    try {
      await addInterfaceUsingPOST({
        ...fields,
      });
      hide();
      message.success('接口创建成功');
      actionRef.current?.reload();
      handleModalOpen(false);
      return true;
    } catch (error: any) {
      hide();
      message.error('接口创建失败,error:' + error.message);
      handleModalOpen(false);
      return false;
    }
  };

  /**
   * @en-US Update node
   * @zh-CN 更新节点
   *
   * @param fields
   */
  const handleUpdate = async (fields: API.InterfaceInfo) => {
    const hide = message.loading('Configuring');
    if (!currentRow) {
      return;
    }
    try {
      await updateInterfaceUsingPOST({
        id: currentRow?.id,
        ...fields,
      });
      hide();
      message.success('修改成功');
      handleUpdateModalOpen(false);
      return true;
    } catch (error: any) {
      hide();
      message.error('修改失败,' + error.message);
      handleUpdateModalOpen(false);
      return false;
    }
  };

  /**
   *  Delete node
   * @zh-CN 删除节点
   *
   * @param record
   */
  const handleRemove = async (record: API.InterfaceInfo) => {
    const hide = message.loading('正在删除');
    if (!record) return true;
    try {
      await deleteInterfaceUsingPOST({
        id: record.id,
      });
      hide();
      message.success('删除接口成功');
      actionRef.current?.reload();
      return true;
    } catch (error: any) {
      hide();
      message.error('删除接口失败,' + error.message);
      return false;
    }
  };

  /**
   * 上线接口
   * @param record
   */
  const handleOnline = async (record: API.IdRequest) => {
    const hide = message.loading('正在上线');
    if (!record) return true;
    try {
      await onlineInterfaceUsingPOST({
        id: record.id,
      });
      hide();
      message.success('上线接口成功');
      actionRef.current?.reload();
      return true;
    } catch (error: any) {
      hide();
      message.error('上线接口失败,' + error.message);
      return false;
    }
  };

  /**
   * 下线接口
   * @param record
   */
  const handleOffline = async (record: API.IdRequest) => {
    const hide = message.loading('正在下线');
    if (!record) return true;
    try {
      await offlineInterfaceUsingPOST({
        id: record.id,
      });
      hide();
      message.success('下线接口成功');
      actionRef.current?.reload();
      return true;
    } catch (error: any) {
      hide();
      message.error('下线接口失败,' + error.message);
      return false;
    }
  };

  /**
   * @en-US International configuration
   * @zh-CN 国际化配置
   * */

  const columns: ProColumns<API.InterfaceInfo>[] = [
    {
      title: 'id',
      dataIndex: 'id',
      valueType: 'index',
      hideInTable: true,
    },
    {
      title: '接口名称',
      dataIndex: 'name',
      valueType: 'text',

      // todo 点击名称进行跳转
      render: (_, record) => (
        <Link key={record.id} to={`/interfaceinfo/${record.id}`}>
          {record.name}
        </Link>
      ),
      ellipsis: true,
    },
    {
      title: '描述',
      dataIndex: 'description',
      valueType: 'textarea',
    },
    {
      title: '接口头像',
      dataIndex: 'avatarUrl',
      valueType: 'image',
      width: 80,
    },
    {
      title: '请求类型',
      dataIndex: 'method',
      filters: true,
      width: 100,
      onFilter: true,
      valueType: 'text',
      key: 'method',
      render: (_, record) => (
        <Tag color={InterfaceRequestMethodEnum[record.method ?? 'default']}>{record.method}</Tag>
      ),
      valueEnum: {
        GET: {
          text: 'GET',
        },
        POST: {
          text: 'POST',
        },
        PUT: {
          text: 'PUT',
        },
        DELETE: {
          text: 'DELETE',
        },
      },
    },
    {
      title: '请求地址',
      dataIndex: 'url',
      valueType: 'text',
      copyable: true,
    },

    {
      title: '创建时间',
      dataIndex: 'createTime',
      valueType: 'dateTime',
      //todo 表单隐藏
      hideInForm: true,
    },
    {
      title: '更新时间',
      dataIndex: 'updateTime',
      valueType: 'dateTime',
      hideInForm: true,
    },
    {
      title: '状态',
      dataIndex: 'status',
      hideInForm: true,
      valueEnum: {
        0: {
          text: '已下线',
          status: 'Default',
        },
        1: {
          text: '已上线',
          status: 'Processing',
        },
      },
    },
    {
      title: '操作',
      dataIndex: 'option',
      valueType: 'option',
      render: (_, record) => [
        <a
          key="config"
          onClick={() => {
            handleUpdateModalOpen(true);
            setCurrentRow(record);
          }}
        >
          修改
        </a>,
        record.status === 0 ? (
          <a
            key="online"
            onClick={() => {
              handleOnline(record);
            }}
          >
            上线
          </a>
        ) : (
          <a
            key="offline"
            onClick={() => {
              handleOffline(record);
            }}
          >
            下线
          </a>
        ),

        <a
          key="delete"
          onClick={() => {
            handleRemove(record);
          }}
        >
          删除
        </a>,
        // <a key="subscribeAlert" href="https://procomponents.ant.design/">
        //   订阅警报
        // </a>,
      ],
    },
  ];
  return (
    <PageContainer>
      <ProTable<API.RuleListItem, API.PageParams>
        headerTitle={'查询表格'}
        actionRef={actionRef}
        rowKey="key"
        search={{
          labelWidth: 120,
        }}
        toolBarRender={() => [
          <Button
            type="primary"
            key="primary"
            onClick={() => {
              handleModalOpen(true);
            }}
          >
            <PlusOutlined /> 新建
          </Button>,
        ]}
        // todo 请求方法的编写
        request={async (
          params: {
            pageSize?: number;
            current?: number;
            keyword?: string;
          },
          sort: Record<string, SortOrder>,
          filter: Record<string, (string | number)[] | null>,
        ) => {
          const res = await listInterfaceInfoByPageUsingGET({
            ...params,
          });
          console.log(res.data);
          if (res?.data) {
            return {
              data: res.data.records || [],
              success: true,
              total: res?.data.total || 0,
            };
          } else {
            return {
              data: [],
              success: false,
              total: 0,
            };
          }
        }}
        columns={columns}
        rowSelection={{
          onChange: (_, selectedRows) => {
            setSelectedRows(selectedRows);
          },
        }}
      />

      {selectedRowsState?.length > 0 && (
        <FooterToolbar
          extra={
            <div>
              已选择{' '}
              <a
                style={{
                  fontWeight: 600,
                }}
              >
                {selectedRowsState.length}
              </a>{' '}
              项 &nbsp;&nbsp;
              <span>
                服务调用次数总计 {selectedRowsState.reduce((pre, item) => pre + item.callNo!, 0)} 万
              </span>
            </div>
          }
        >
          <Button
            onClick={async () => {
              await handleRemove(selectedRowsState);
              setSelectedRows([]);
              actionRef.current?.reloadAndRest?.();
            }}
          >
            批量删除
          </Button>
          <Button type="primary">批量审批</Button>
        </FooterToolbar>
      )}
      <ModalForm
        title={'新建接口'}
        width="400px"
        open={createModalOpen}
        onOpenChange={handleModalOpen}
        onFinish={async (value) => {
          const success = await handleAdd(value as API.RuleListItem);
          if (success) {
            handleModalOpen(false);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
          setCurrentRow(undefined);
        }}
      >
        <ProFormText
          rules={[
            {
              required: true,
              message: '规则名称为必填项',
            },
          ]}
          width="md"
          name="name"
        />
        <ProFormTextArea width="md" name="desc" />
      </ModalForm>
      <UpdateModel
        values={currentRow || {}}
        columns={columns}
        onSubmit={async (value) => {
          const success = await handleUpdate(value);
          if (success) {
            handleUpdateModalOpen(false);
            setCurrentRow(undefined);
            if (actionRef.current) {
              actionRef.current.reload();
            }
          }
        }}
        onCancel={() => {
          handleUpdateModalOpen(false);
          if (!showDetail) {
            setCurrentRow(undefined);
          }
        }}
        visible={updateModalOpen}
      />

      <Drawer
        width={600}
        open={showDetail}
        onClose={() => {
          setCurrentRow(undefined);
          setShowDetail(false);
        }}
        closable={false}
      >
        {currentRow?.name && (
          <ProDescriptions<API.RuleListItem>
            column={2}
            title={currentRow?.name}
            request={async () => ({
              data: currentRow || {},
            })}
            params={{
              id: currentRow?.name,
            }}
            columns={columns as ProDescriptionsItemProps<API.RuleListItem>[]}
          />
        )}
      </Drawer>
      <CreateModel
        columns={columns}
        onCancel={() => {
          handleModalOpen(false);
        }}
        onSubmit={(values) => {
          return handleAdd(values);
        }}
        visible={createModalOpen}
      />
    </PageContainer>
  );
};
export default TableList;
